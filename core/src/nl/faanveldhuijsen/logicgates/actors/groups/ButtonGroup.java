package nl.faanveldhuijsen.logicgates.actors.groups;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import nl.faanveldhuijsen.logicgates.actors.BaseActor;
import nl.faanveldhuijsen.logicgates.actors.TextActor;
import nl.faanveldhuijsen.logicgates.figures.ButtonFigure;
import nl.faanveldhuijsen.logicgates.logics.ButtonAction;
import nl.faanveldhuijsen.logicgates.logics.Clickable;
import nl.faanveldhuijsen.logicgates.logics.Draggable;

public class ButtonGroup extends BaseGroup implements Draggable, Clickable {


    private ButtonAction action;
    public boolean disabled = false;

    public ButtonGroup(float x, float y, int width, int height) {
        super();

        setSize(width, height);
        setPosition(x, y);

        ButtonFigure figure = new ButtonFigure((int) getWidth(), (int) getHeight());
        BaseActor actor = new BaseActor(0, 0);
        actor.setSprite(figure.getTexture(), figure.getWidth(), figure.getHeight());

        addActor(actor);
    }

    public ButtonGroup(String title, float x, float y, int w, int h) {
        this(x, y, w, h);

        TextActor text = new TextActor(title, Color.BLACK, getWidth() / 2, getHeight() / 2);
        addActor(text);
    }

//    public ButtonGroup(Icon somethingsomething, float x, float y) {
//        // TODO add button possibility
//    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (disabled) {
            parentAlpha = 0.5f;
        }
        super.draw(batch, parentAlpha);
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        return true;
    }

    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        if (action != null) {
            action.onClick();
        }
    }

    public void onAction(ButtonAction action) {
        this.action = action;
    }

    @Override
    public void dragStart(InputEvent event, float x, float y, int pointer) {
        if (action != null) {
            action.dragStart();
        }
    }

    @Override
    public void drag(InputEvent event, float x, float y, int pointer) {
        if (action != null) {
            action.drag(event);
        }
    }

    @Override
    public void dragStop(InputEvent event, float x, float y, int pointer, DragListener self) {
        if (action != null) {
            action.dragStop();
        }
    }
}
