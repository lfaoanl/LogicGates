package nl.faanveldhuijsen.logicgates.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.ColorAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.Array;
import nl.faanveldhuijsen.logicgates.actors.groups.BaseGroup;
import nl.faanveldhuijsen.logicgates.figures.CircleFigure;
import nl.faanveldhuijsen.logicgates.logics.*;

public class SwitchActor extends BaseActor implements SwitchLogic, Clickable, Draggable {

    private static final Color COLOR_ON = new Color(0.7f, 0.2f, 0.2f, 1.0f);
    private static final Color COLOR_OFF = new Color(0.9f, 0.9f, 0.9f, 1.0f);

    public final LogicType type;
    private final int size;

    private boolean on = false;

    private ConnectionActor connection;
    private SwitchActor[] sources = new SwitchActor[2];

    private ConnectionActor tmpConnection;

    public SwitchActor(float x, float y, int size, LogicType type) {
        this(x, y, size, type, null);
    }

    public SwitchActor(float x, float y, int size, LogicType type, SwitchActor... sources) {
        super(x, y);//x - (size * 2), y - (size * 2));

        CircleFigure circleFigure = new CircleFigure(size, COLOR_OFF);
        setSprite(circleFigure.getTexture(), size * 4, size * 4);

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
            case NOT:
                return !getSourceOutput(0);
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
        if (connection != null) {
            System.out.println(connection.getPath());
        }
        if (type != LogicType.SWITCH) {
            return false;
        }
        ScaleToAction scale = new ScaleToAction();
        scale.setDuration(0.2f);
        scale.setScale(0.9f);
        addAction(scale);
        return true;
    }

    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        setOutput(!getOutput());

        ScaleToAction scale = new ScaleToAction();
        scale.setDuration(0.2f);
        scale.setScale(1.0f);
        addAction(scale);
    }

    @Override
    public void dragStart(InputEvent event, float x, float y, int pointer) {
        tmpConnection = new ConnectionActor(this);
        tmpConnection.setColor(Color.LIGHT_GRAY);
        getStage().addActor(tmpConnection);
    }

    @Override
    public void drag(InputEvent event, float x, float y, int pointer) {
        tmpConnection.paint(event.getStageX(), event.getStageY());

        if (type != LogicType.COPY) {
            SwitchActor target = findHoveredActor(getStage().getActors());
            if (target != null && target.type == LogicType.COPY) {
                tmpConnection.snapTo(target.getPosition());
//                ScaleToAction scale = new ScaleToAction();
//                scale.setScale(1.5f);
//                scale.setDuration(0.5f);
//                target.addAction(scale);
//                hoveredActor = target;
            }
//            if (target == null && hoveredActor != null) {
//                hoveredActor.setScale(1.0f);
//            }
        }
    }

    @Override
    public void dragStop(InputEvent event, float x, float y, int pointer, DragListener self) {
        if (type != LogicType.COPY) {
            SwitchActor target = findHoveredActor(getStage().getActors());
            if (target != null && target.type == LogicType.COPY) {
                target.setSource(tmpConnection, this);
            }
            tmpConnection.remove();
            tmpConnection = null;
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

    public void setSource(ConnectionActor tmp, SwitchActor... sources) {
        this.sources = sources;

        if (sources.length == 1) {
            SwitchActor source = sources[0];

            if (source == null) {
                removeConnection();
                return;
            }
            boolean groupInstance = getParent() instanceof BaseGroup;
            if (!groupInstance || getParent() != source.getParent()) {
                if (connection != null) {
                    connection.remove();
                }
                connection = new ConnectionActor(this, source);
                ConnectionPoints path = tmp.getPath();
                path.reverse();
                connection.setPath(path);
                getStage().addActor(connection);
            }
        }
    }

    private void removeConnection() {
        if (connection != null){
            connection.remove();
            connection = null;
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

    public void clearConnections() {
        this.removeConnection();
        this.sources = new SwitchActor[2];
    }
}
