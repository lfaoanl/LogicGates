package nl.faanveldhuijsen.logicgates.logics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.ColorAction;
import nl.faanveldhuijsen.logicgates.actors.groups.ButtonGroup;
import nl.faanveldhuijsen.logicgates.actors.groups.GateGroup;
import nl.faanveldhuijsen.logicgates.gates.CustomGate;

import java.lang.reflect.Constructor;

public class AddGateAction extends ButtonAction {

    private final Stage stage;
    private final ButtonGroup parent;
    private final Class<? extends GateGroup> gateClass;
    private String customGate = null;
    private GateGroup gate;

    public AddGateAction(Stage stage, ButtonGroup parent, Class<? extends GateGroup> gateClass, String gateId) {
        this(stage, parent, gateClass);
        this.customGate = gateId;
    }
    public AddGateAction(Stage stage, ButtonGroup parent, Class<? extends GateGroup> gateClass) {
        this.stage = stage;
        this.parent = parent;
        this.gateClass = gateClass;
    }

    @Override
    public void dragStart() {
        gate = createGateInstance();
        stage.addActor(gate);

        Color oldColor = new Color(gate.getColor());
        Color newColor = new Color(oldColor);
        newColor.a = 0;
        gate.setColor(newColor);

        ColorAction fadeIn = new ColorAction();

        fadeIn.setColor(gate.getColor());
        fadeIn.setEndColor(oldColor);
        fadeIn.setDuration(0.2f);
        gate.addAction(fadeIn);
        gate.setZIndex(10);
    }

    private GateGroup createGateInstance() {
        if (customGate != null) {
            return new CustomGate(parent.getX(), parent.getY(), customGate);
        }
        try {
            Constructor<?> construct = gateClass.getConstructor(float.class, float.class);
            return (GateGroup) construct.newInstance(parent.getX(), parent.getY());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void drag(InputEvent event) {
        gate.setPosition(event.getStageX() - (gate.getWidth() / 2), event.getStageY() - (gate.getHeight() / 2));
    }

    @Override
    public void dragStop() {
        gate.setGridPosition(gate.getX(), gate.getY());
    }
}
