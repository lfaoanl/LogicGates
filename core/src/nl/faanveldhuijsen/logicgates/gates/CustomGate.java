package nl.faanveldhuijsen.logicgates.gates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import nl.faanveldhuijsen.logicgates.actors.SwitchActor;
import nl.faanveldhuijsen.logicgates.actors.groups.GateGroup;
import nl.faanveldhuijsen.logicgates.data.GateData;
import nl.faanveldhuijsen.logicgates.data.SwitchData;
import nl.faanveldhuijsen.logicgates.logics.LogicType;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomGate extends GateGroup {

    HashMap<String, SwitchActor> customSwitches = new HashMap<>();
    HashMap<String, CustomGate> customGates = new HashMap<>();

    public CustomGate(float x, float y, String gateId) {
        super(x, y);

        setName(gateId);

        GateData data = loadData(gateId);

        this.amountInputs = data.switchCount.get("input");
        this.amountOutputs = data.switchCount.get("output");

        setSize();
        createMainBlock();

        createTitle(data.title);

        addSwitches(data.switches);

        connectSwitches(data.switches);
    }

    protected void addSwitches(ArrayList<SwitchData> data) {
        for (SwitchData switchData : data) {
            if (switchData.id.contains("custom")) {
                customGates.put(switchData.id, new CustomGate(0, 0, switchData.gate));
                continue;
            }
            if (switchData.id.contains("input")) {
                customSwitches.put(switchData.id, addInput());
                continue;
            }

            if (switchData.id.contains("output")) {
                customSwitches.put(switchData.id, addOutput(switchData.logicType));
                continue;
            }

            customSwitches.put(switchData.id, new SwitchActor(switchData.logicType));
        }
    }

    private void connectSwitches(ArrayList<SwitchData> switches) {
        for (SwitchData switchData : switches) {
            if (switchData.id.contains("custom")) {
                CustomGate gate = customGates.get(switchData.id);

                ArrayList<SwitchActor> inputs = gate.getInputs();
                for (int i = 0; i < inputs.size(); i++) {
                    SwitchActor gateInput = inputs.get(i);
                    gateInput.setSource(customSwitches.get(switchData.sources.get(i)));
                }
                continue;
            }

            // Inputs don't have sources, so skip
            if (switchData.id.contains("input")) {
                continue;
            }

            // Parses sources for outputs and others
            SwitchActor switchActor = customSwitches.get(switchData.id);
            switchActor.setSource(parseSources(switchData));
        }
    }

    private SwitchActor[] parseSources(SwitchData switchData) {
        SwitchActor[] sources = new SwitchActor[]{null, null};

        for (int i = 0; i < switchData.sources.size(); i++) {
            String src = switchData.sources.get(i);

            if (src.contains("custom")) {
                sources[i] = customGates.get(src).getOutputs().get(0);
                continue;
            }
            sources[i] = customSwitches.get(src);
        }
        return sources;
    }

    private GateData loadData(String gateId) {
        Json json = new Json();
        return json.fromJson(GateData.class, Gdx.files.local("data/gates/" + gateId + ".json"));
    }
}
