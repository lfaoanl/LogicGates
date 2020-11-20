package nl.faanveldhuijsen.logicgates.stages;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import nl.faanveldhuijsen.logicgates.actors.AndGate;
import nl.faanveldhuijsen.logicgates.actors.SwitchActor;
import nl.faanveldhuijsen.logicgates.figures.PixelFigure;
import nl.faanveldhuijsen.logicgates.logics.LogicType;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class BoardStage extends Stage {

    public final Color background = new Color(0.2f, 0.2f, 0.2f, 1.0f);
    public final PixelFigure pixel;

    public BoardStage() {
        super(new ScreenViewport());
        pixel = new PixelFigure();
    }

    public void init() {
        SwitchActor input1 = new SwitchActor(20, getHeight() / 3, 10, LogicType.SWITCH);
        SwitchActor input2 = new SwitchActor(20, getHeight() / 3 * 2, 10, LogicType.SWITCH);

        AndGate andGate = new AndGate((int) (getWidth() / 2) - 48, (int) (getHeight() / 2) - 48);

        Actor output = new SwitchActor(getWidth() - 50, (getHeight() / 2) + 5, 10, LogicType.COPY);


        addActor(input1);
        addActor(input2);
        addActor(andGate);
        addActor(output);

    }

    @Override
    public void draw() {

        grid();

        super.draw();
    }

    private void grid() {
        Batch batch = getBatch();
        batch.begin();
        ShapeDrawer drawer = pixel.getDrawer(batch);
        drawer.setColor(Color.GRAY);
        int steps = 32;
        int x = -(steps / 2);
        while (x < getWidth()) {
            drawer.line(x, 0, x, getViewport().getScreenHeight());
            x += steps;
        }
        int y = -(steps / 2);
        while (y < getHeight()){
            drawer.line(0, y, getViewport().getScreenWidth(), y);
            y += steps;
        }
        batch.end();
    }


}
