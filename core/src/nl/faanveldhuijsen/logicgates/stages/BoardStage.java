package nl.faanveldhuijsen.logicgates.stages;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import nl.faanveldhuijsen.logicgates.actors.InputActor;

public class BoardStage extends Stage {

    public BoardStage() {
        super(new ScreenViewport());
    }

    public void init() {
        Actor input1 = new InputActor(20, 200, 16);
        Actor input2 = new InputActor(20, 100, 16);

        addActor(input1);
        addActor(input2);
    }
}
