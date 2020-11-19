package nl.faanveldhuijsen.logicgates.stages;

import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import nl.faanveldhuijsen.logicgates.actors.AndGate;
import nl.faanveldhuijsen.logicgates.actors.ConnectionActor;
import nl.faanveldhuijsen.logicgates.actors.groups.GateGroup;
import nl.faanveldhuijsen.logicgates.actors.SwitchActor;
import nl.faanveldhuijsen.logicgates.logics.LogicType;
import nl.faanveldhuijsen.logicgates.logics.SwitchLogic;

public class BoardStage extends Stage {


    public BoardStage() {
        super(new ScreenViewport());
    }

    public void init() {
        SwitchLogic input1 = new SwitchActor(20, 200, 10, LogicType.SWITCH);
        SwitchLogic input2 = new SwitchActor(20, 100, 10, LogicType.SWITCH);

        Actor andGate = new AndGate(100, 150);
        Actor andGate2 = new AndGate(100, 150);
        Actor andGate3 = new AndGate(100, 150);

        Actor output = new SwitchActor(300, 150, 10, LogicType.COPY);



//        Actor line = new ConnectionActor(200, 300);


        addActor((Actor) input1);
        addActor((Actor) input2);
        addActor(andGate);
        addActor(andGate2);
        addActor(andGate3);
        addActor(output);
    }
}
