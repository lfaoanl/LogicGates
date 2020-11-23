package nl.faanveldhuijsen.logicgates.actors.groups;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Json;
import nl.faanveldhuijsen.logicgates.gates.AndGate;
import nl.faanveldhuijsen.logicgates.gates.CustomGate;
import nl.faanveldhuijsen.logicgates.gates.NotGate;
import nl.faanveldhuijsen.logicgates.logics.AddGateAction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ButtonCarouselGroup extends BaseGroup {

    private static class ButtonData extends HashMap<String, String> {}

    private final ArrayList<ButtonGroup> buttons = new ArrayList<>();

    public ButtonCarouselGroup(Stage stage, float x, float y, float width, float height) {
        setPosition(x, y);
        setSize(width, height);

        final ButtonGroup addAndGate = new ButtonGroup("AND", 0, 0, 64, 32);
        addAndGate.onAction(new AddGateAction(stage, addAndGate, AndGate.class));
        addActor(addAndGate);
        buttons.add(addAndGate);

        final ButtonGroup addNotGate = new ButtonGroup("NOT", 96, 0, 64, 32);
        addNotGate.onAction(new AddGateAction(stage, addNotGate, NotGate.class));
        addActor(addNotGate);
        buttons.add(addNotGate);

        ButtonData buttonsData = getButtonsData();

        for(Map.Entry<String, String> entry : buttonsData.entrySet()) {
            String gateId = entry.getKey();
            String title = entry.getValue();
            addButton(stage, gateId, title);
        }
    }

    public void addButton(Stage stage, String gateId, String title) {
        final ButtonGroup custom = new ButtonGroup(title, buttons.size() * 96, 0, 64, 32);
        custom.onAction(new AddGateAction(stage, custom, CustomGate.class, gateId));
        addActor(custom);
        buttons.add(custom);
        // TODO edit json file
    }

    private ButtonData getButtonsData() {
        Json json = new Json();
        return json.fromJson(ButtonData.class, Gdx.files.internal("data/gate_buttons.json"));
    }
}
