package nl.faanveldhuijsen.logicgates.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import nl.faanveldhuijsen.logicgates.logics.Clickable;
import nl.faanveldhuijsen.logicgates.logics.Draggable;

public abstract class BaseActor extends Actor {

    protected ClickListener clickListener;
    protected Sprite sprite;
    private float width;
    private float height;

    public BaseActor(float x, float y) {
        setX(x);
        setY(y);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.setColor(this.getColor());
        sprite.draw(batch);
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Texture texture, float width, float height) {
        sprite = new Sprite(texture);
        this.width = width;
        this.height = height;
        sprite.setSize(width, height);
        sprite.setPosition(getX(), getY());
        setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
    }

    protected void enableTouchDown() {
        if (this instanceof Clickable) {
            setTouchable(Touchable.enabled);
            addListener(clickListener = new ClickListener() {
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    ((Clickable) BaseActor.this).touchUp(event, x, y, pointer, button);
                }

                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return ((Clickable) BaseActor.this).touchDown(event, x, y, pointer, button);
                }
            });
        }
    }

    protected void enableDraggable() {
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
}
