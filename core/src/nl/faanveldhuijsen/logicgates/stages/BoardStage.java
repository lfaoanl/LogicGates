package nl.faanveldhuijsen.logicgates.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import nl.faanveldhuijsen.logicgates.actions.CreateGateAction;
import nl.faanveldhuijsen.logicgates.actors.ConnectionActor;
import nl.faanveldhuijsen.logicgates.actors.groups.ButtonCarouselGroup;
import nl.faanveldhuijsen.logicgates.actors.groups.ButtonGroup;
import nl.faanveldhuijsen.logicgates.actors.groups.GateGroup;
import nl.faanveldhuijsen.logicgates.figures.PixelFigure;
import nl.faanveldhuijsen.logicgates.actions.ButtonAction;
import nl.faanveldhuijsen.logicgates.logics.LogicType;
import nl.faanveldhuijsen.logicgates.logics.SwitchList;
import space.earlygrey.shapedrawer.ShapeDrawer;

import java.util.ArrayList;

public class BoardStage extends Stage {

    public final Color background = new Color(0.2f, 0.2f, 0.2f, 1.0f);
    public final PixelFigure pixel;

    public final SwitchList inputs;
    public final SwitchList outputs;

    public ButtonCarouselGroup buttons;

    public BoardStage() {
        super(new ExtendViewport(640, 420));
        pixel = new PixelFigure();
        inputs = new SwitchList(this, 2, 5, getHeight());
        outputs = new SwitchList(this, 1, 5, getHeight());
        buttons = new ButtonCarouselGroup(this, 64, 16, getWidth() - 128, 64);
    }

    public void init() {
        final ButtonGroup addInput = new ButtonGroup("+i", 16, 16, 32, 32);
        final ButtonGroup addOutput = new ButtonGroup("+o", getWidth() - 48, 16, 32, 32);

        inputs.setDefault(addInput, LogicType.SWITCH, addInput.getX());
        outputs.setDefault(addOutput, LogicType.COPY, addOutput.getX());

        inputs.reset();
        outputs.reset();

        addInput.onAction(new ButtonAction() {
            @Override
            public void onClick() {
//                ((OrthographicCamera) getCamera()).zoom += 0.02f;
                inputs.add();
            }
        });

        addOutput.onAction(new ButtonAction() {
            @Override
            public void onClick() {
                outputs.add();
            }
        });

        addActor(addInput);
        addActor(addOutput);

        addActor(buttons);

        ButtonGroup convertToGate = new ButtonGroup("Create gate", getWidth() - 128 - 96, getHeight() - 64, 128 + 64, 48);
        convertToGate.onAction(new CreateGateAction(this));
        addActor(convertToGate);
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
        while (y < getHeight()) {
            drawer.line(0, y, getViewport().getScreenWidth(), y);
            y += steps;
        }
        batch.end();
    }


    public void reset() {
        this.clear();
        this.init();
    }
}
