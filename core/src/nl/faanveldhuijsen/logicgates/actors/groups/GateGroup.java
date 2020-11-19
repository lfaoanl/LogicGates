package nl.faanveldhuijsen.logicgates.actors.groups;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import nl.faanveldhuijsen.logicgates.actors.BaseActor;
import nl.faanveldhuijsen.logicgates.actors.GateActor;
import nl.faanveldhuijsen.logicgates.actors.SwitchActor;
import nl.faanveldhuijsen.logicgates.figures.GateFigure;
import nl.faanveldhuijsen.logicgates.logics.Draggable;
import nl.faanveldhuijsen.logicgates.logics.LogicType;
import nl.faanveldhuijsen.logicgates.logics.SwitchLogic;

import java.util.ArrayList;

public class GateGroup extends BaseGroup implements Draggable {

    protected final Color GATE_COLOR = new Color(0.5f, 1.0f, 0.5f, 1.0f);

    protected BaseActor main;

    protected int amountInputs;
    protected int amountOutputs;
    protected ArrayList<SwitchActor> inputs = new ArrayList<>();
    protected ArrayList<SwitchActor> outputs = new ArrayList<>();

    private Vector2 startDrag;

    public GateGroup(int x, int y, int amountInputs, int amountOutputs) {
        super();

        setWidth(48);
        float height = getWidth() / 2 * Math.max(amountInputs, amountOutputs);
        setHeight(height);
        setPosition(x, y);

        main = new GateActor(0, 0);

        GateFigure gateFigure = new GateFigure((int) getWidth(), (int) getHeight(), GATE_COLOR);
        main.setSprite(gateFigure.getTexture());
        gateFigure.dispose();

        addActor(main);

        this.amountInputs = amountInputs;
        this.amountOutputs = amountOutputs;
    }

    public SwitchActor addInput() {
        return addChild(0, amountInputs, inputs, LogicType.COPY, (SwitchActor) null);
    }

    public SwitchActor addOutput(LogicType type, SwitchActor... sources) {
        return addChild(getWidth(), amountOutputs, outputs, type, sources);
    }

    private SwitchActor addChild(float x, int amount, ArrayList<SwitchActor> actors, LogicType type, SwitchActor... sources) {
        float heightOffset = getHeight() / (amount + 1);
        SwitchActor switchActor = new SwitchActor(x, heightOffset * (actors.size() + 1), 5, type, sources);

        addActor(switchActor);
        actors.add(switchActor);

        return switchActor;
    }

    public void addChildren() {
        inputs = addChildren(0, amountInputs);
        outputs = addChildren((int) getWidth(), amountOutputs);
    }

    private ArrayList<SwitchActor> addChildren(int x, int amount) {
        ArrayList<SwitchActor> returnable = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            float heightOffset = getHeight() / (amount + 1);
            SwitchActor actor = new SwitchActor(x, (int) heightOffset * (i + 1), 5, LogicType.COPY);
            returnable.add(actor);
            addActor(actor);
        }
        return returnable;
    }

    @Override
    public void dragStart(InputEvent event, float x, float y, int pointer) {
        setScale(1.1f);
        startDrag = new Vector2(getX() - event.getStageX(), getY() - event.getStageY());
    }

    @Override
    public void drag(InputEvent event, float x, float y, int pointer) {
        float x1 = getX() + x + startDrag.x;
        float y1 = getY() + y + startDrag.y;
        setPosition(x1, y1);
    }

    @Override
    public void dragStop(InputEvent event, float x, float y, int pointer, DragListener self) {
        setScale(1.0f);
    }
}