package nl.faanveldhuijsen.logicgates.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.ColorAction;
import com.badlogic.gdx.utils.compression.lzma.Base;

public class BaseActor extends Actor {

    protected Sprite sprite;

    public BaseActor(int x, int y) {
        setX(x);
        setY(y);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.setColor(this.getColor());
        sprite.draw(batch);
    }

    public void setSprite(Texture texture) {
        sprite = new Sprite(texture);
        sprite.setPosition(getX(), getY());
        setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
    }

    public void enableTouchDown() {
        setTouchable(Touchable.enabled);
        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return BaseActor.this.touchDown(event, x, y, pointer, button);
            }
        });
    }

    protected boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        return false;
    }
}
