package nl.faanveldhuijsen.logicgates.gates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import nl.faanveldhuijsen.logicgates.actors.SwitchActor;
import nl.faanveldhuijsen.logicgates.actors.groups.GateGroup;
import nl.faanveldhuijsen.logicgates.logics.LogicType;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomGate extends GateGroup {

    protected static class SwitchData {
        String id;
        LogicType logicType;
        ArrayList<String> sources;
    }

    private static class GateData {
        String title;
        HashMap<String, Integer> switchCount;
        ArrayList<SwitchData> inputs;
        ArrayList<SwitchData> outputs;
        ArrayList<SwitchData> switches;
    }

    HashMap<String, SwitchActor> customInputs = new HashMap<>();
    HashMap<String, SwitchActor> customOutputs = new HashMap<>();
    HashMap<String, SwitchActor> customSwitches = new HashMap<>();

    public CustomGate(float x, float y, String gateId) {
        super(x, y);

        GateData data = loadData(gateId);

        this.amountInputs = data.switchCount.get("input");
        this.amountOutputs = data.switchCount.get("output");

        setSize();
        createMainBlock();

        createTitle(data.title);

        addInputs(data.inputs);
        addSwitches(data.switches);
        addOutputs(data.outputs);

        connectSwitches(data.switches);
    }

    protected void addInputs(ArrayList<SwitchData> data) {
        for (SwitchData switchData : data) {
            customInputs.put(switchData.id, addInput());
        }
    }

    protected void addSwitches(ArrayList<SwitchData> data) {
        for (SwitchData switchData : data) {
            customSwitches.put(switchData.id, new SwitchActor(switchData.logicType));
        }
    }

    protected void addOutputs(ArrayList<SwitchData> outputs) {
        for (SwitchData switchData : outputs) {

            SwitchActor[] sources = parseSources(switchData);

            SwitchActor output = addOutput(switchData.logicType, sources);
            customOutputs.put(switchData.id, output);
        }
    }

    private void connectSwitches(ArrayList<SwitchData> switches) {
        for (SwitchData switchData : switches) {
            SwitchActor switchActor = customSwitches.get(switchData.id);
            switchActor.setSource(parseSources(switchData));
        }
    }

    private SwitchActor[] parseSources(SwitchData switchData) {
        SwitchActor[] sources = new SwitchActor[]{null, null};
        for (int i = 0; i < switchData.sources.size(); i++) {
            String src = switchData.sources.get(i);

            if (src.startsWith("input")) {
                sources[i] = customInputs.get(src);
            } else {
                sources[i] = customSwitches.get(src);
            }
        }
        return sources;
    }

    private GateData loadData(String gateId) {
        Json json = new Json();
        return json.fromJson(GateData.class, Gdx.files.internal("data/gates/" + gateId + ".json"));
    }
}
