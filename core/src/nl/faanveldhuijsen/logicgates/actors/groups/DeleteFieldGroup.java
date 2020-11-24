package nl.faanveldhuijsen.logicgates.actors.groups;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ColorAction;
import nl.faanveldhuijsen.logicgates.actors.BaseActor;
import nl.faanveldhuijsen.logicgates.actors.TextActor;
import nl.faanveldhuijsen.logicgates.figures.ButtonFigure;

public class DeleteFieldGroup extends BaseGroup {


    private final BaseActor main;
    public final Color INVISIBLE = new Color(0, 0, 0, 0);
    public final Color VISIBLE = new Color(0, 0, 0, 0.9f);

    public DeleteFieldGroup(float x, float y, int width, int height) {
        super();

        setSize(width, height);
        setPosition(x, y);


        ButtonFigure figure = new ButtonFigure((int) getWidth(), (int) getHeight());
        main = new BaseActor(0, 0);
        main.setSprite(figure.getTexture(), figure.getWidth(), figure.getHeight());
        setColor(INVISIBLE);

        addActor(main);
    }

    public DeleteFieldGroup(String title, float x, float y, int w, int h) {
        this(x, y, w, h);

        TextActor text = new TextActor(title, Color.WHITE, getWidth() / 2, getHeight() / 2);
        addActor(text);
    }

    @Override
    public void act(float delta) {
        main.setColor(getColor());
        super.act(delta);
    }

    public void fadeIn() {
        ColorAction fadeIn = new ColorAction();
        fadeIn.setColor(getColor());
        fadeIn.setEndColor(VISIBLE);
        fadeIn.setDuration(0.2f);
        addAction(fadeIn);
    }

    public void fadeOut() {
        ColorAction fadeOut = new ColorAction();
        fadeOut.setColor(getColor());
        fadeOut.setEndColor(INVISIBLE);
        fadeOut.setDuration(0.2f);
        addAction(Actions.sequence(
                fadeOut,
                Actions.removeActor()
        ));
    }
}
