package nl.faanveldhuijsen.logicgates.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.model.data.ModelTexture;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.ColorAction;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.Array;
import nl.faanveldhuijsen.logicgates.figures.CircleFigure;
import nl.faanveldhuijsen.logicgates.figures.ConnectionFigure;
import nl.faanveldhuijsen.logicgates.logics.Clickable;
import nl.faanveldhuijsen.logicgates.logics.Draggable;
import nl.faanveldhuijsen.logicgates.logics.LogicType;
import nl.faanveldhuijsen.logicgates.logics.SwitchLogic;

public class SwitchActor extends BaseActor implements SwitchLogic, Clickable, Draggable {

    private static final Color COLOR_ON = new Color(0.7f, 0.2f, 0.2f, 1f);
    private static final Color COLOR_OFF = new Color(0.9f, 0.9f, 0.9f, 1.0f);

    public final LogicType type;
    private boolean on = false;

    private SwitchActor[] sources = new SwitchActor[2];
    private Sprite connectionSprite;

    public SwitchActor(float x, float y, int size, LogicType type) {
        this(x, y, size, type, null);
    }

    public SwitchActor(float x, float y, int size, LogicType type, SwitchActor... sources) {
        super(x - (size * 2), y - (size * 2));

        CircleFigure circleFigure = new CircleFigure(size, COLOR_OFF);
        setSprite(circleFigure.getTexture());

        this.sources = sources;
        this.type = type;

        circleFigure.dispose();



        enableTouchDown();
        if (type != LogicType.COPY) {
            enableDraggable();
        }

    }

    @Override
    public void act(float delta) {
        super.act(delta);

        this.setOutput(this.getOutput());
    }

    @Override
    public boolean getOutput() {
        if (type == LogicType.SWITCH) {
            return on;
        }
        if (sources == null) {
            return false;
        }
        switch (type) {
            case AND:
                return getSourceOutput(0) && getSourceOutput(1);
            case OR:
                return getSourceOutput(0) || getSourceOutput(1);
            case COPY:
                return getSourceOutput(0);
        }
        return false;
    }

    private boolean getSourceOutput(int index) {
        SwitchLogic source = sources[index];
        if (source == null) {
            return false;
        }
        return source.getOutput();
    }

    @Override
    public void setOutput(boolean newOutput) {
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
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        if (type != LogicType.SWITCH) {
            return false;
        }
        setOutput(!getOutput());
        return true;
    }

    @Override
    public void dragStart(InputEvent event, float x, float y, int pointer) {

    }

    @Override
    public void drag(InputEvent event, float x, float y, int pointer) {

    }

    @Override
    public void dragStop(InputEvent event, float x, float y, int pointer, DragListener self) {
        if (type != LogicType.COPY) {
            SwitchActor target = findHoveredActor(getStage().getActors());
            if (target != null && target.type == LogicType.COPY) {
                target.setSource(this);
                System.out.println(target);
            }
        }
    }

    public static SwitchActor findHoveredActor(Array<Actor> actors) {
        for (Actor actor : actors) {
            if (actor instanceof Clickable && ((Clickable) actor).getClickListener().isOver()) {
                if (actor instanceof Group) {
                    return findHoveredActor(((Group) actor).getChildren());
                }
                return (SwitchActor) actor;
            }
        }
        return null;
    }

    public void setSource(SwitchActor... sources) {
        this.sources = sources;
    }

    public void setSource(int index, SwitchActor source) {
        this.sources[index] = source;
    }
}
