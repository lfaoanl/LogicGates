package nl.faanveldhuijsen.logicgates.logics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import nl.faanveldhuijsen.logicgates.actors.BaseActor;
import nl.faanveldhuijsen.logicgates.actors.TextActor;
import nl.faanveldhuijsen.logicgates.actors.groups.BaseGroup;
import nl.faanveldhuijsen.logicgates.figures.ButtonFigure;

public abstract class TextInput extends BaseGroup implements InputProcessor {

    protected final TextActor textActor;
    private boolean opened = false;

    public TextInput(float x, float y, float width, float height) {
        setSize(width, height);
        setPosition(x - (width / 2), y - (height / 2));

        ButtonFigure figure = new ButtonFigure((int) getWidth(), (int) getHeight(), 8);
        BaseActor main = new BaseActor(0, 0);
        main.setSprite(figure.getTexture(), figure.getWidth(), figure.getHeight());

        addActor(main);

        textActor = new TextActor("", Color.BLACK, getWidth() / 2, getHeight() / 2);
        addActor(textActor);
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
                return true;
            }
            if (keycode == Input.Keys.ENTER) {
                close();
                output(text);
            }
        }
        if (keycode >= 29 && keycode <= 54) {
            textActor.setText(text + Input.Keys.toString(keycode));
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
        if (opened) {
            close();
        } else {
            open();
        }
        return true;
    }

    public void open() {
        if (opened) {
            return;
        }
        Gdx.input.setOnscreenKeyboardVisible(true);
        MoveByAction move = new MoveByAction();
        move.setAmountY(getStage().getHeight() / 4);
        move.setDuration(0.2f);
        addAction(move);
        opened = true;
    }

    public void close() {
        if (!opened) {
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
}
