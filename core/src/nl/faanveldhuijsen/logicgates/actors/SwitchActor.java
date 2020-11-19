package nl.faanveldhuijsen.logicgates.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.ColorAction;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.Array;
import nl.faanveldhuijsen.logicgates.figures.CircleFigure;
import nl.faanveldhuijsen.logicgates.logics.Clickable;
import nl.faanveldhuijsen.logicgates.logics.Draggable;
import nl.faanveldhuijsen.logicgates.logics.LogicType;
import nl.faanveldhuijsen.logicgates.logics.SwitchLogic;

public class SwitchActor extends BaseActor implements SwitchLogic, Clickable, Draggable {

    private static final Color COLOR_ON = new Color(0.7f, 0.2f, 0.2f, 1f);
    private static final Color COLOR_OFF = new Color(0.9f, 0.9f, 0.9f, 1.0f);

    public final LogicType type;
    private final int size;

    private boolean on = false;

    private ConnectionActor[] connection = new ConnectionActor[2];
    private SwitchActor[] sources = new SwitchActor[2];

    public SwitchActor(float x, float y, int size, LogicType type) {
        this(x, y, size, type, null);
    }

    public SwitchActor(float x, float y, int size, LogicType type, SwitchActor... sources) {
        super(x,y);//x - (size * 2), y - (size * 2));

        CircleFigure circleFigure = new CircleFigure(size, COLOR_OFF);
        setSprite(circleFigure.getTexture());

        this.sources = sources;
        this.type = type;
        this.size = size;

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

        Color color = getOutputColor();
        colorAction.setEndColor(color);

        colorAction.setDuration(0.05f);
        this.addAction(colorAction);
    }

    public Color getOutputColor() {
        if (this.getOutput()) {
            return COLOR_ON;
        }
        return COLOR_OFF;
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
//        connection[1] = new ConnectionActor(this, x, y);
//        getStage().addActor(connection[1]);
    }

    @Override
    public void drag(InputEvent event, float x, float y, int pointer) {
//        connection.paint(event.getStageX(), event.getStageY());
    }

    @Override
    public void dragStop(InputEvent event, float x, float y, int pointer, DragListener self) {
        if (type != LogicType.COPY) {
            SwitchActor target = findHoveredActor(getStage().getActors());
            if (target != null && target.type == LogicType.COPY) {
                target.setSource(this);
//                target.connection = connection;
//                connection.setEnd(target);
                return;
            }
        }
//        connection.remove();
//        connection = null;
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

        if (sources == null) {
            connection = null;
            return;
        }
        for (int i = 0; i < sources.length; i++) {
            SwitchActor source = sources[i];

            if (source == null) {
                continue;
            }
            if (getParent() != source.getParent()) {
                connection[i] = new ConnectionActor(this, source);
                getStage().addActor(connection[i]);
            }
        }
    }

    public Vector2 getPosition() {
        Vector2 center = new Vector2(getX() + (size * 2), getY() + (size * 2));
        if (hasParent()) {
            Vector2 vector2 = new Vector2(getParent().getX(), getParent().getY());
            return vector2.add(center);
        }
        return center;
    }

}
