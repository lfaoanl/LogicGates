package nl.faanveldhuijsen.logicgates.actions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import nl.faanveldhuijsen.logicgates.actors.BaseActor;
import nl.faanveldhuijsen.logicgates.actors.SwitchActor;
import nl.faanveldhuijsen.logicgates.actors.groups.ButtonCarouselGroup;
import nl.faanveldhuijsen.logicgates.data.GateData;
import nl.faanveldhuijsen.logicgates.data.SwitchData;
import nl.faanveldhuijsen.logicgates.logics.LogicType;
import nl.faanveldhuijsen.logicgates.logics.SwitchList;
import nl.faanveldhuijsen.logicgates.stages.BoardStage;

import java.util.ArrayList;
import java.util.HashMap;

public class CreateGateAction extends ButtonAction {

    private final BoardStage stage;

    ArrayList<SwitchData> dataInputs = new ArrayList<>();
    ArrayList<SwitchData> dataSwitches = new ArrayList<>();
    ArrayList<SwitchData> dataOutputs = new ArrayList<>();

    public CreateGateAction(BoardStage stage) {
        this.stage = stage;
    }

    @Override
    public void onClick() {

//        BaseActor getTitle = new TextInput(stage.getWidth() / 2, stage.getHeight() / 2);

        // Loop through outputs and get switchData
        for (SwitchActor output : stage.outputs.getSwitches()) {
            SwitchData switchData = getSwitchData(output, true);
        }

        String gateId = "gate_" + stage.buttons.nextId();
        GateData gateData = getGateData();
        writeJson(gateData, gateId);

        stage.buttons.addButton(stage, gateId, "TEST");
        stage.buttons.updateButtonData();

        stage.reset();
    }

    private GateData getGateData() {
        GateData gateData = new GateData();

        gateData.title = "TEST";

        gateData.switchCount = new HashMap<>();
        gateData.switchCount.put("input", dataInputs.size());
        gateData.switchCount.put("output", dataOutputs.size());

        gateData.inputs = dataInputs;
        gateData.switches = dataSwitches;
        gateData.outputs = dataOutputs;
        return gateData;
    }

    private void writeJson(GateData gateData, String gateId) {
        Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        String jsonText = json.toJson(gateData);
        FileHandle file = Gdx.files.local("data/gates/" + gateId + ".json");
        file.writeString(jsonText, false);
    }

    private SwitchData getSwitchData(SwitchActor actor, boolean isOutput) {
        SwitchData switchData = new SwitchData();
        switchData.logicType = actor.type;

        SwitchActor[] sources = actor.getSources();
        if (stage.inputs.getSwitches().contains(actor)) {
            switchData.id = "input_" + dataInputs.size();
            dataInputs.add(switchData);
        } else if (sources != null) {
            switchData.sources = new ArrayList<>();
            for (SwitchActor switchActor : sources) {
                SwitchActor concurrentActor = switchActor.type == LogicType.COPY ? switchActor.getSources()[0] : switchActor;
                SwitchData concurrent = getSwitchData(concurrentActor, false);
                switchData.sources.add(concurrent.id);
            }

            if (isOutput) {
                switchData.id = "output_" + dataOutputs.size();
                dataOutputs.add(switchData);
            } else {
                switchData.id = "switch_" + dataSwitches.size();
                dataSwitches.add(switchData);
            }
        }

        return switchData;
    }
}
