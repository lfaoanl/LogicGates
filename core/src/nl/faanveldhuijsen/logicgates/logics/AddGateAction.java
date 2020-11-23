package nl.faanveldhuijsen.logicgates.logics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.ColorAction;
import nl.faanveldhuijsen.logicgates.gates.AndGate;
import nl.faanveldhuijsen.logicgates.actors.groups.ButtonGroup;
import nl.faanveldhuijsen.logicgates.actors.groups.GateGroup;

public class AddGateAction extends ButtonAction {

    private GateGroup gate;
    private final Stage stage;
    private final ButtonGroup parent;

    public AddGateAction(Stage stage, ButtonGroup parent) {
        this.stage = stage;
        this.parent = parent;
    }

    @Override
    public void dragStart() {
        gate = new AndGate(parent.getX(), parent.getY());
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

    @Override
    public void drag(InputEvent event) {
        gate.setPosition(event.getStageX() - (gate.getWidth() / 2), event.getStageY() - (gate.getHeight() / 2));
    }

    @Override
    public void dragStop() {
        gate.setGridPosition(gate.getX(), gate.getY());
    }
}
