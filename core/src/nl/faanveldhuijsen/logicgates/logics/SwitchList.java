package nl.faanveldhuijsen.logicgates.logics;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import nl.faanveldhuijsen.logicgates.actors.SwitchActor;
import nl.faanveldhuijsen.logicgates.actors.groups.ButtonGroup;
import nl.faanveldhuijsen.logicgates.stages.BoardStage;

import java.util.ArrayList;

public class SwitchList {

    private ArrayList<SwitchActor> switches = new ArrayList<>();
    private final float height;
    private final int minimum;
    private final int maximum;
    private final Stage stage;

    private ButtonGroup button;
    private LogicType type;
    private float x;

    public SwitchList(Stage stage, int minimum, int maximum, float height) {
        this.height = height;
        this.stage = stage;
        this.minimum = minimum;
        this.maximum = maximum;
    }

    public void add() {
        this.add(true);
    }

    public void add(boolean animation) {
        this.add(stage, type, animation);
    }

    public void add(Stage stage, LogicType type, boolean animation) {
        if (full()) {
            return;
        }
        for (int i = 0; i < switches.size(); i++) {
            SwitchActor actor = switches.get(i);
            float y = getY(i);

            if (animation) {
                MoveToAction moveTo = getMoveToAction(actor, y);
                actor.addAction(moveTo);
            } else {
                actor.setY(y);
            }
        }

        float y = animation ? button.getY() : getY(switches.size());
        SwitchActor newActor = new SwitchActor(button.getX(), y, 10, type);
        if (animation) {

            MoveToAction moveTo = getMoveToAction(button, getY(switches.size()));
            newActor.addAction(moveTo);
        }

        switches.add(newActor);
        stage.addActor(newActor);

        button.disabled = full();
    }

    public MoveToAction getMoveToAction(Actor actor, float y) {
        MoveToAction moveTo = new MoveToAction();
        moveTo.setX(actor.getX());
        moveTo.setY(y);
        moveTo.setDuration(0.1f);
        return moveTo;
    }

    public float getY(int i) {
        // Step size is equal to the height / by amount of switches + the new switch + 1
        float steps = height / (switches.size() + 2);
        return height - (steps * (i + 1));
    }

    public boolean full() {
        return switches.size() >= maximum;
    }

    public void setDefault(ButtonGroup button, LogicType aSwitch, float x) {
        this.type = aSwitch;
        this.x = x;
        this.button = button;

    }

    public void reset() {
        for (int i = 0; i < switches.size(); i++) {
            SwitchActor actor = switches.get(i);
            remove(actor);
        }

        for (int i = 0; i < minimum; i++) {
            add(false);
        }
    }

    public void remove(SwitchActor actor) {
        // TODO HashMap size does not decrease, convert to Array<String[2]>
        switches.remove(actor);
        actor.remove();
    }

    public ArrayList<SwitchActor> getSwitches() {
        return switches;
    }
}
