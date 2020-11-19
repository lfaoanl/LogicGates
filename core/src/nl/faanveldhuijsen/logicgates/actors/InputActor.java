package nl.faanveldhuijsen.logicgates.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.ColorAction;
import nl.faanveldhuijsen.logicgates.figures.CircleFigure;

public class InputActor extends BaseActor implements GateLogic {

    private static final Color COLOR_ON = new Color(0.7f, 0.2f, 0.2f, 1f);
    private static final Color COLOR_OFF = Color.WHITE;

    private boolean on = false;


    public InputActor(int x, int y, int size) {
        super(x, y);

        CircleFigure circleFigure = new CircleFigure(size, COLOR_OFF);
        setSprite(circleFigure.getTexture());

        circleFigure.dispose();

        enableTouchDown();
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        setOutput(!getOutput());
        return true;
    }

    private void setOutput(boolean newOutput) {
        ColorAction colorAction = new ColorAction();
        this.on = newOutput;

        Color color = COLOR_OFF;
        if (this.getOutput()) {
            color = COLOR_ON;
        }

        colorAction.setEndColor(color);

        colorAction.setDuration(0.05f);
        this.addAction(colorAction);
    }

    @Override
    public boolean getOutput() {
        return this.on;
    }

}
