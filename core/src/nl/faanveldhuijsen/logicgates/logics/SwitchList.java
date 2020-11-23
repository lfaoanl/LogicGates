package nl.faanveldhuijsen.logicgates.logics;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import nl.faanveldhuijsen.logicgates.actors.SwitchActor;
import nl.faanveldhuijsen.logicgates.stages.BoardStage;

import java.util.ArrayList;

public class SwitchList {

    private ArrayList<SwitchActor> switches = new ArrayList<>();
    private final float height;
    private final int minimum;
    private final int maximum;
    private final Stage stage;

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
        this.add(stage, type, x, animation);
    }

    public void add(Stage stage, LogicType type, float x, boolean animation) {
        if (switches.size() >= maximum) {
            return;
        }
        float steps = height / (switches.size() + 2);
        for (int i = 0; i < switches.size(); i++) {
            SwitchActor actor = switches.get(i);
            float y = steps * (i + 1);

            if (animation) {
                MoveToAction moveTo = new MoveToAction();
                moveTo.setX(actor.getX());
                moveTo.setY(y);
                moveTo.setDuration(0.1f);
                actor.addAction(moveTo);
            } else {
                actor.setY(y);
            }
        }
        SwitchActor newActor = new SwitchActor(x, steps * (switches.size() + 1), 10, type);
        switches.add(newActor);
        stage.addActor(newActor);
    }

    public void setDefault(LogicType aSwitch, float x) {
        this.type = aSwitch;
        this.x = x;
    }

    public void reset() {
        for (SwitchActor actor : switches) {
            remove(actor);
        }

        for (int i = 0; i < minimum; i++) {
            add(false);
        }
    }

    public void remove(SwitchActor actor) {
        switches.remove(actor);
        actor.remove();
    }
}
