package nl.faanveldhuijsen.logicgates.gates;

import nl.faanveldhuijsen.logicgates.actors.groups.GateGroup;
import nl.faanveldhuijsen.logicgates.logics.LogicType;

public class NotGate extends GateGroup {
    public NotGate(float x, float y) {
        super(x, y, "NOT", 1, 1);

        addOutput(LogicType.NOT, addInput());
    }
}
