package nl.faanveldhuijsen.logicgates.logics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.Array;
import nl.faanveldhuijsen.logicgates.actors.SwitchActor;

public interface Draggable {


    void dragStart(InputEvent event, float x, float y, int pointer);

    void drag(InputEvent event, float x, float y, int pointer);

    void dragStop(InputEvent event, float x, float y, int pointer, DragListener self);

}
