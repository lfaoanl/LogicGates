package nl.faanveldhuijsen.logicgates.figures;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class PixelFigure extends BaseFigure {

    private final TextureRegion pixelRegion;

    public PixelFigure() {
        super(1, 1);

        setColor(Color.WHITE);
        drawPixel(0, 0);

        pixelRegion = new TextureRegion(getTexture(), 0, 0, 1, 1);
    }

    public ShapeDrawer getDrawer(Batch batch) {
        return new ShapeDrawer(batch, pixelRegion);
    }
}
