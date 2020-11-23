package nl.faanveldhuijsen.logicgates.actors.groups;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import nl.faanveldhuijsen.logicgates.actors.BaseActor;
import nl.faanveldhuijsen.logicgates.actors.TextActor;
import nl.faanveldhuijsen.logicgates.figures.ButtonFigure;
import nl.faanveldhuijsen.logicgates.logics.ClickAction;

public class ButtonGroup extends BaseGroup {


    private ClickAction clickAction;

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
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        return true;
    }

    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        if (clickAction != null) {
            clickAction.onClick();
        }
    }

    public void onClick(ClickAction clickAction) {
        this.clickAction = clickAction;
    }
}
