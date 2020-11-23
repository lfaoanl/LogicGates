package nl.faanveldhuijsen.logicgates.data;

import nl.faanveldhuijsen.logicgates.gates.CustomGate;

import java.util.ArrayList;
import java.util.HashMap;

public class GateData {
        public String title;
        public HashMap<String, Integer> switchCount;
        public ArrayList<SwitchData> inputs;
        public ArrayList<SwitchData> outputs;
        public ArrayList<SwitchData> switches;
}
