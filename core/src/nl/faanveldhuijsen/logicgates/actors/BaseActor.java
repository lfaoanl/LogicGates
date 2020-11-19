package nl.faanveldhuijsen.logicgates.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import nl.faanveldhuijsen.logicgates.logics.Clickable;
import nl.faanveldhuijsen.logicgates.logics.Draggable;

public abstract class BaseActor extends Actor {

    protected ClickListener clickListener;
    protected Sprite sprite;

    public BaseActor(float x, float y) {
        setX(x);
        setY(y);

        if (this instanceof Clickable) {
            enableTouchDown();
        }
        if (this instanceof Draggable) {
            enableDraggable();
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.setColor(this.getColor());
        sprite.draw(batch);
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Texture texture) {
        sprite = new Sprite(texture);
        sprite.setPosition(getX(), getY());
        setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
    }

    private void enableTouchDown() {
        if (this instanceof Clickable) {
            setTouchable(Touchable.enabled);
            addListener(clickListener = new ClickListener() {
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return ((Clickable) BaseActor.this).touchDown(event, x, y, pointer, button);
                }
            });
        }
    }

    private void enableDraggable() {
        if (this instanceof Draggable) {
            setTouchable(Touchable.enabled);

            addListener(new DragListener() {
                @Override
                public void dragStart(InputEvent event, float x, float y, int pointer) {
                    ((Draggable) BaseActor.this).dragStart(event, x, y, pointer);
                }

                @Override
                public void drag(InputEvent event, float x, float y, int pointer) {
                    ((Draggable) BaseActor.this).drag(event, x, y, pointer);
                }

                @Override
                public void dragStop(InputEvent event, float x, float y, int pointer) {
                    ((Draggable) BaseActor.this).dragStop(event, x, y, pointer, this);
                }
            });

        }
    }

    public ClickListener getClickListener() {
        return clickListener;
    }

//    @Override
//    protected void positionChanged() {
//        if (sprite != null) {
//            sprite.setPosition(getX(), getY());
//        }
//    }
}