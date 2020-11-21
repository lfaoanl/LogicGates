package nl.faanveldhuijsen.logicgates.actors;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import nl.faanveldhuijsen.logicgates.actors.groups.GateGroup;
import nl.faanveldhuijsen.logicgates.logics.Draggable;

public class GateActor extends BaseActor implements Draggable {

    private Vector2 startDrag;

    public GateActor(int x, int y) {
        super(x, y);

        enableDraggable();
    }

    @Override
    public void dragStart(InputEvent event, float x, float y, int pointer) {
        GateGroup parent = (GateGroup) getParent();
        parent.setScale(1.1f);
        startDrag = new Vector2(parent.getX() - event.getStageX(), parent.getY() - event.getStageY());

//        for (SwitchActor input : parent.getInputs()) {
//            input.clearConnections();
//        }
    }

    @Override
    public void drag(InputEvent event, float x, float y, int pointer) {
        Group parent = getParent();
        float x1 = parent.getX() + x + startDrag.x;
        float y1 = parent.getY() + y + startDrag.y;
        parent.setPosition(x1, y1);
    }

    @Override
    public void dragStop(InputEvent event, float x, float y, int pointer, DragListener self) {
        getParent().setScale(1.0f);

        float xPos = getParent().getX() + x + startDrag.x;
        float yPos = getParent().getY() + y + startDrag.y;
        int gridSize = 16;
        xPos = Math.round(xPos / gridSize) * gridSize;
        yPos = Math.round(yPos / gridSize) * gridSize;

        getParent().setPosition(xPos, yPos);
    }
}
