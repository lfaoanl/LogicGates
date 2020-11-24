package nl.faanveldhuijsen.logicgates.figures;

import com.badlogic.gdx.graphics.Color;

public class ButtonFigure extends BaseFigure {

    public ButtonFigure(int width, int height, int radius) {
        super(width + 5, height + 5);

        setColor(Color.WHITE);

//        setColor(new Color(0f, 0f, 0f, 0.8f));
//        fillRectangle(5, 5, width + 5, height + 5);
//        setColor(new Color(0.4f, 0.4f, 0.4f, 0.8f));
//        fillRectangle(0, 0, width, height);
        fillRectangle(0, radius, getWidth(), getHeight()-2*radius);
        fillRectangle(radius, 0, getWidth() - 2*radius, getHeight());
        fillCircle(radius, radius, radius);
        fillCircle(radius, getHeight()-radius, radius);
        fillCircle(getWidth()-radius, radius, radius);
        fillCircle(getWidth()-radius, getHeight()-radius, radius);

    }

    public ButtonFigure(int width, int height) {
        super(width + 5, height + 5);

        setColor(Color.WHITE);
        fillRectangle(0, 0, getWidth(), getHeight());
    }
}
