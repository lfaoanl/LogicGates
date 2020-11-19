package nl.faanveldhuijsen.logicgates;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import nl.faanveldhuijsen.logicgates.stages.BoardStage;

public class LogicGates extends ApplicationAdapter {

    BoardStage stage;

    @Override
    public void create() {
        stage = new BoardStage();

        stage.init();
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
