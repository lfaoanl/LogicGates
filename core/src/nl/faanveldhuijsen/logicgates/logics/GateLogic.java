package nl.faanveldhuijsen.logicgates.logics;

public interface GateLogic {

    boolean getOutput(int outputIndex);

    void addSource(int inputIndex, SwitchLogic source);
}
