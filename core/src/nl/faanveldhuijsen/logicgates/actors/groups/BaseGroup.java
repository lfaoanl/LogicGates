package nl.faanveldhuijsen.logicgates.actors.groups;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import nl.faanveldhuijsen.logicgates.logics.Clickable;
import nl.faanveldhuijsen.logicgates.logics.Draggable;

public abstract class BaseGroup extends Group {

    protected ClickListener clickListener;

    public BaseGroup() {
        if (this instanceof Clickable) {
            enableTouchDown();
        }
        if (this instanceof Draggable) {
            enableDraggable();
        }
    }

    public void enableTouchDown() {
            setTouchable(Touchable.enabled);
            addListener(clickListener = new ClickListener() {
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    ((Clickable) BaseGroup.this).touchUp(event, x, y, pointer, button);
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return ((Clickable) BaseGroup.this).touchDown(event, x, y, pointer, button);
                }
            });
    }

    public void enableDraggable() {
        if (this instanceof Draggable) {
            setTouchable(Touchable.enabled);

            addListener(new DragListener() {
                @Override
                public void dragStart(InputEvent event, float x, float y, int pointer) {
                    ((Draggable) BaseGroup.this).dragStart(event, x, y, pointer);
                }

                @Override
                public void drag(InputEvent event, float x, float y, int pointer) {
                    ((Draggable) BaseGroup.this).drag(event, x, y, pointer);
                }

                @Override
                public void dragStop(InputEvent event, float x, float y, int pointer) {
                    ((Draggable) BaseGroup.this).dragStop(event, x, y, pointer, this);
                }
            });
        }
    }

    @Override
    protected void positionChanged() {
        super.positionChanged();
    }

    public ClickListener getClickListener() {
        return clickListener;
    }

}
