package nl.faanveldhuijsen.logicgates.figures;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public abstract class BaseFigure extends Pixmap {

    private Texture texture;

    public BaseFigure(int width, int height) {
        super(width, height, Format.RGBA8888);
    }

    public Texture getTexture() {
        if (texture == null) {
            texture = new Texture(this);
        }
        return texture;
    }
}
