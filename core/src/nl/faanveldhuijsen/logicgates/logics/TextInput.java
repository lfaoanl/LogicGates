package nl.faanveldhuijsen.logicgates.logics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import nl.faanveldhuijsen.logicgates.LogicGates;
import nl.faanveldhuijsen.logicgates.actions.ButtonAction;
import nl.faanveldhuijsen.logicgates.actors.BaseActor;
import nl.faanveldhuijsen.logicgates.actors.TextActor;
import nl.faanveldhuijsen.logicgates.actors.groups.BaseGroup;
import nl.faanveldhuijsen.logicgates.actors.groups.ButtonGroup;
import nl.faanveldhuijsen.logicgates.figures.ButtonFigure;

public abstract class TextInput extends BaseGroup implements InputProcessor {

    protected final TextActor textActor;
    private final RepeatAction caretBlink;
    private final ButtonGroup close;
    protected BaseActor caretActor;
    private boolean opened = false;

    public TextInput(float x, float y, float width, float height) {
        setSize(width, height);
        setPosition(x - (width / 2), y - (height / 2));

        ButtonFigure figure = new ButtonFigure((int) getWidth(), (int) getHeight(), 8);
        BaseActor main = new BaseActor(0, 0);
        main.setSprite(figure.getTexture(), figure.getWidth(), figure.getHeight());

        addActor(main);

        textActor = new TextActor("", Color.BLACK, 16, getHeight() / 2);
        textActor.centered = false;
        addActor(textActor);

        Pixmap caret = new Pixmap(4, 32, Pixmap.Format.RGBA8888);
        caret.setColor(Color.BLACK);
        caret.fill();

        caretActor = new BaseActor(18, (getHeight() / 2) - 16);
        caretActor.setSprite(new Texture(caret), caret.getWidth(), caret.getHeight());
        addActor(caretActor);

        caretBlink = Actions.forever(Actions.sequence(
                Actions.delay(0.4f, Actions.fadeOut(0.1f)),
                Actions.delay(0.4f, Actions.fadeIn(0.1f))
        ));
        caretActor.addAction(caretBlink);

        close = new ButtonGroup("X", getWidth() - 24, getHeight() - 24, 16, 16);
        addActor(close);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    @Override
    public boolean keyDown(int keycode) {
        TextActor textActor = TextInput.this.textActor;
        String text = textActor.getText();

        if (text.length() >= 1) {
            if (keycode == Input.Keys.BACKSPACE) {
                textActor.setText(text.substring(0, text.length() - 1));

                caretActor.setX(textActor.getX() + textActor.textWidth + 2);
                return true;
            }
            if (keycode == Input.Keys.ENTER) {
                close();
                output(text);
            }
        }
        if (keycode >= 29 && keycode <= 54) {
            textActor.setText(text + Input.Keys.toString(keycode));

            caretActor.setX(textActor.getX() + textActor.textWidth + 2);
            return true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector2 coords = getStage().screenToStageCoordinates(new Vector2(screenX, screenY));
        if (close.withinBounds(coords.x - getX(), coords.y - getY())) {
            close();
            remove();
            return true;
        }

        if (withinBounds(coords.x, coords.y)) {
            open();
        } else {
            close();
        }
        return true;
    }

    public void open() {
        if (opened || LogicGates.desktop) {
            return;
        }

        Gdx.input.setOnscreenKeyboardVisible(true);
        MoveByAction move = new MoveByAction();
        move.setAmountY(getStage().getHeight() / 4);
        move.setDuration(0.1f);
        addAction(move);
        opened = true;
    }

    public void close() {
        if (!opened || LogicGates.desktop) {
            return;
        }

        Gdx.input.setOnscreenKeyboardVisible(false);
        MoveByAction move = new MoveByAction();
        move.setAmountY(-(getStage().getHeight() / 4));
        move.setDuration(0.2f);
        addAction(move);
        opened = false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    protected abstract void output(String text);

    @Override
    public boolean remove() {
        Gdx.input.setInputProcessor(getStage());
        return super.remove();
    }
}
