package nl.faanveldhuijsen.logicgates.logics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import nl.faanveldhuijsen.logicgates.actors.SwitchActor;

import java.util.ArrayList;

public class SwitchList {

    private ArrayList<SwitchActor> switches = new ArrayList<>();
    private final float height;

    public SwitchList(float height) {
        this.height = height;
    }

    public SwitchActor add(LogicType type, float x) {
        float steps = height / (switches.size() + 2);
        for (int i = 0; i < switches.size(); i++) {
            SwitchActor actor = switches.get(i);

            MoveToAction moveTo = new MoveToAction();
            moveTo.setX(actor.getX());
            moveTo.setY(steps * (i + 1));
            moveTo.setDuration(0.1f);
            actor.addAction(moveTo);
        }
        SwitchActor newActor = new SwitchActor(x, steps * (switches.size() + 1), 10, type);
        switches.add(newActor);
        return newActor;
    }
}
