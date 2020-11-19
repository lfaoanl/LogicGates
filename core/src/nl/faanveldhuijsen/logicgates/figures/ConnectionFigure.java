package nl.faanveldhuijsen.logicgates.figures;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class ConnectionFigure extends BaseFigure {

    public ConnectionFigure(Stage stage, Vector2 pos1, Vector2 pos2) {
        super((int)stage.getWidth(), (int) stage.getHeight());

        setColor(Color.WHITE);
        drawLine((int) pos1.x, (int) pos1.y, (int) pos2.x, (int) pos2.y);
        System.out.println(pos1);
        System.out.println(pos2);
    }
}
