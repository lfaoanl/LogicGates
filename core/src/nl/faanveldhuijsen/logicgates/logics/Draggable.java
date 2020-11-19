package nl.faanveldhuijsen.logicgates.logics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;

public interface Draggable {


    void dragStart(InputEvent event, float x, float y, int pointer);

    void drag(InputEvent event, float x, float y, int pointer);

    void dragStop(InputEvent event, float x, float y, int pointer, DragListener self);
}
