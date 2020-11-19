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
import nl.faanveldhuijsen.logicgates.figures.CircleFigure;

public class InputActor extends Actor implements GateLogic {

    private static final Color red = new Color(0.7f, 0.2f, 0.2f, 1f);
    private static final Color gray = new Color(0.2f, 0.2f, 0.2f, 1f);
    private static final Color white = new Color(1f, 1f, 1f, 1f);

    private boolean on = false;

    Sprite sprite;

    public InputActor(int x, int y, int size) {
        super();

        setX(x);
        setY(y);

        CircleFigure circleFigure = new CircleFigure(size, white);
        Texture texture = circleFigure.getTexture();
        sprite = new Sprite(texture);

        setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
        setTouchable(Touchable.enabled);

        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                ColorAction colorAction = new ColorAction();
                InputActor.this.on = !InputActor.this.on;

                Color color = white;
                if (InputActor.this.getOutput()) {
                    color = red;
                }

                colorAction.setEndColor(color);

                colorAction.setDuration(0.05f);
                InputActor.this.addAction(colorAction);
                return true;
            }
        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.setColor(this.getColor());
        sprite.draw(batch);
    }

    @Override
    public boolean getOutput() {
        return this.on;
    }

    @Override
    protected void positionChanged() {
        sprite.setX(getX());
        sprite.setY(getY());
        super.positionChanged();
    }
}
