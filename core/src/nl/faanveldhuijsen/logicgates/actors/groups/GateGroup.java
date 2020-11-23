package nl.faanveldhuijsen.logicgates.actors.groups;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import nl.faanveldhuijsen.logicgates.actors.BaseActor;
import nl.faanveldhuijsen.logicgates.actors.GateActor;
import nl.faanveldhuijsen.logicgates.actors.SwitchActor;
import nl.faanveldhuijsen.logicgates.actors.TextActor;
import nl.faanveldhuijsen.logicgates.figures.GateFigure;
import nl.faanveldhuijsen.logicgates.logics.Clickable;
import nl.faanveldhuijsen.logicgates.logics.Draggable;
import nl.faanveldhuijsen.logicgates.logics.LogicType;
import nl.faanveldhuijsen.logicgates.logics.SwitchLogic;

import java.util.ArrayList;

public class GateGroup extends BaseGroup implements Clickable {

    protected final Color GATE_COLOR = new Color(0.5f, 1.0f, 0.5f, 1.0f);

    protected BaseActor main;

    protected int amountInputs;
    protected int amountOutputs;
    protected ArrayList<SwitchActor> inputs = new ArrayList<>();
    protected ArrayList<SwitchActor> outputs = new ArrayList<>();

    public GateGroup(float x, float y) {
        setPosition(x, y);
    }

    public GateGroup(float x, float y, String text, int amountInputs, int amountOutputs) {
        this(x, y);

        this.amountInputs = amountInputs;
        this.amountOutputs = amountOutputs;
        setSize();

        createMainBlock();

        createTitle(text);
    }

    protected void createTitle(String text) {
        TextActor title = new TextActor(text, Color.BLACK, getWidth() / 2, getHeight() / 2);
        addActor(title);
    }

    protected void createMainBlock() {
        main = new GateActor(0, 0);

        GateFigure gateFigure = new GateFigure(Math.max(amountInputs, amountOutputs));
        main.setSprite(gateFigure.getTexture(), getWidth(), getHeight());
        gateFigure.dispose();

        addActor(main);
    }

    protected void setSize() {
        setWidth(96);
        float height = getWidth() / 2 * Math.max(amountInputs, amountOutputs);
        setHeight(height);
    }

    public SwitchActor addInput() {
        return addChild(0, amountInputs, inputs, LogicType.COPY, (SwitchActor) null);
    }

    public SwitchActor addOutput(LogicType type, SwitchActor... sources) {
        return addChild(getWidth(), amountOutputs, outputs, type, sources);
    }

    private SwitchActor addChild(float x, int amount, ArrayList<SwitchActor> actors, LogicType type, SwitchActor... sources) {
        int size = 10;
        float heightOffset = getHeight() / (amount + 1);
        SwitchActor switchActor = new SwitchActor(x - (size * 2), (heightOffset * (actors.size() + 1)) - (size * 2), size, type, sources);

        addActor(switchActor);
        actors.add(switchActor);

        return switchActor;
    }

    protected void addInputs() {
        for (int i = 0; i < amountInputs; i++) {
            addInput();
        }
    }

    protected void addOutputs() {
        for (int i = 0; i < amountOutputs; i++) {
            addOutput(LogicType.COPY, inputs.get(0));
        }
    }

    public ArrayList<SwitchActor> getInputs() {
        return inputs;
    }

    public ArrayList<SwitchActor> getOutputs() {
        return outputs;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

    }

    public void setGridPosition(float xPos, float yPos) {
        int gridSize = 16;
        xPos = Math.round(xPos / gridSize) * gridSize;
        yPos = Math.round(yPos / gridSize) * gridSize;
        setPosition(xPos, yPos);
    }
}
