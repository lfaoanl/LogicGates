package nl.faanveldhuijsen.logicgates.figures;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public class CircleFigure extends BaseFigure {

    public CircleFigure(int radius, Color color) {
        super(radius * 2,radius * 2);

        setColor(color);
        fillCircle(radius, radius, radius);
    }

    /*

    Pixmap pixmap = new Pixmap( 64, 64, Pixmap.Format.RGBA8888);
        pixmap.setColor( 0.7f, 0.2f, 0.2f, 1 );
        pixmap.fillCircle( 32, 32, 16);
        texture = new Texture(pixmap);

     */
}
