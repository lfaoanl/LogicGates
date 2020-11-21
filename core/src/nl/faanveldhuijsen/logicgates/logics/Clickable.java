package nl.faanveldhuijsen.logicgates.logics;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import nl.faanveldhuijsen.logicgates.actors.groups.BaseGroup;

public interface Clickable {

    boolean touchDown(InputEvent event, float x, float y, int pointer, int button);

    void touchUp(InputEvent event, float x, float y, int pointer, int button);

    ClickListener getClickListener();
}
