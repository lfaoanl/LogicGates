package nl.faanveldhuijsen.logicgates.gates;

import nl.faanveldhuijsen.logicgates.actors.SwitchActor;
import nl.faanveldhuijsen.logicgates.actors.groups.GateGroup;
import nl.faanveldhuijsen.logicgates.logics.GateLogic;
import nl.faanveldhuijsen.logicgates.logics.LogicType;
import nl.faanveldhuijsen.logicgates.logics.SwitchLogic;

public class AndGate extends GateGroup implements GateLogic {

    SwitchActor output;

    SwitchActor input1;
    SwitchActor input2;

    public AndGate(float x, float y) {
        super(x, y, "AND", 2, 1);

        input1 = addInput();
        input2 = addInput();

        output = addOutput(LogicType.AND, input1, input2);

    }

    public SwitchActor getOutput() {
        return this.outputs.get(0);
    }

    public SwitchActor getFirstInput() {
        return this.inputs.get(0);
    }

    public SwitchActor getSecondInput() {
        return this.inputs.get(1);
    }

}
