package nl.faanveldhuijsen.logicgates.figures;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GateFigure extends BaseFigure {

    private final Pixmap gateIconPixmap;

    public GateFigure(int rows) {
        super(512, 256 * rows);

        rows--;
        Texture gateIcon = new Texture(Gdx.files.internal("gate_icon.png"));
        gateIcon.getTextureData().prepare();
        gateIconPixmap = gateIcon.getTextureData().consumePixmap();

        drawTop();

        if (rows >= 1) {
            for (int i = 0; i < rows; i++) {
                drawMiddle(i);
            }
        }
        drawBottom(rows);

//        int radius = 8;
//        fillRectangle(0, radius, getWidth(), getHeight()-2*radius);
//        fillRectangle(radius, 0, getWidth() - 2*radius, getHeight());
//        fillCircle(radius, radius, radius);
//        fillCircle(radius, getHeight()-radius, radius);
//        fillCircle(getWidth()-radius, radius, radius);
//        fillCircle(getWidth()-radius, getHeight()-radius, radius);


    }

    private void drawTop() {
        drawPixmap(gateIconPixmap, 0, 0, 0, 0, 512, 128);
    }

    private void drawMiddle(int row) {
        drawPixmap(gateIconPixmap, 0, 128 + (row * 256), 0, 128, 512, 256);
    }

    private void drawBottom(int rows) {
        drawPixmap(gateIconPixmap, 0, 128 + (rows * 256), 0, 384, 512, 128);
    }


}
