package nl.faanveldhuijsen.logicgates.figures;

import com.badlogic.gdx.graphics.Color;

public class GateFigure extends BaseFigure {

    public GateFigure(int width, int height, Color color) {
        super(width, height);

        setColor(color);

        int radius = 8;
        fillRectangle(0, radius, getWidth(), getHeight()-2*radius);
        fillRectangle(radius, 0, getWidth() - 2*radius, getHeight());
        fillCircle(radius, radius, radius);
        fillCircle(radius, getHeight()-radius, radius);
        fillCircle(getWidth()-radius, radius, radius);
        fillCircle(getWidth()-radius, getHeight()-radius, radius);
    }
}
