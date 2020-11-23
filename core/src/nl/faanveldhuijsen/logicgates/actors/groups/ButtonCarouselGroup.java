package nl.faanveldhuijsen.logicgates.actors.groups;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import nl.faanveldhuijsen.logicgates.gates.AndGate;
import nl.faanveldhuijsen.logicgates.gates.CustomGate;
import nl.faanveldhuijsen.logicgates.gates.NotGate;
import nl.faanveldhuijsen.logicgates.actions.AddGateAction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ButtonCarouselGroup extends BaseGroup {

    private static class ButtonData extends HashMap<String, String> {}

    public static final ArrayList<ButtonGroup> buttons = new ArrayList<>();

    public ButtonCarouselGroup(Stage stage, float x, float y, float width, float height) {
        setPosition(x, y);
        setSize(width, height);

        final ButtonGroup addAndGate = new ButtonGroup("AND", 0, 0, 64, 32);
        addAndGate.onAction(new AddGateAction(stage, addAndGate, AndGate.class));
        addActor(addAndGate);

        final ButtonGroup addNotGate = new ButtonGroup("NOT", 96, 0, 64, 32);
        addNotGate.onAction(new AddGateAction(stage, addNotGate, NotGate.class));
        addActor(addNotGate);

        ButtonData buttonsData = getButtonsData();

        for(Map.Entry<String, String> entry : buttonsData.entrySet()) {
            String gateId = entry.getKey();
            String title = entry.getValue();
            addButton(stage, gateId, title);
        }
    }

    public void addButton(Stage stage, String gateId, String title) {
        final ButtonGroup custom = new ButtonGroup(title, (buttons.size() + 2) * 96, 0, 64, 32);
        custom.setName(gateId);
        custom.onAction(new AddGateAction(stage, custom, CustomGate.class, gateId));
        addActor(custom);
        buttons.add(custom);
    }

    public void updateButtonData() {
        FileHandle file = Gdx.files.local("data/gate_buttons.json");
        Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        String jsonText = json.toJson(parseButtonData());
        file.writeString(jsonText, false);
    }

    private ButtonData getButtonsData() {
        Json json = new Json();
        FileHandle buttonData = Gdx.files.local("data/gate_buttons.json");
        return json.fromJson(ButtonData.class, buttonData);
    }

    private ButtonData parseButtonData() {
        ButtonData buttonData = new ButtonData();
        for (ButtonGroup button : buttons) {
            buttonData.put(button.getName(), button.getTitle());
        }
        return buttonData;
    }

    public int nextId() {
        return buttons.size();
    }
}
