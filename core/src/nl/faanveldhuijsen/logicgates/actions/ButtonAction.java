package nl.faanveldhuijsen.logicgates.actions;


import com.badlogic.gdx.scenes.scene2d.InputEvent;

public abstract class ButtonAction {

    public void onClick() {}

    public void dragStart() {}

    public void drag(InputEvent event) {}

    public void dragStop() {}
}
