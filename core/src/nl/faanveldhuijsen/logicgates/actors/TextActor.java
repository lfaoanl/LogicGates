package nl.faanveldhuijsen.logicgates.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector2;

public class TextActor extends BaseActor {

    private String text;
    private final BitmapFont font = new BitmapFont();
    private Vector2 offset;

    public TextActor(String text, Color color, float x, float y) {
        super(x, y);
        setText(text);
        setColor(color);
    }

    private void setText(String text) {
        GlyphLayout layout = new GlyphLayout(font, text);
        float width = layout.width;
        float height = layout.height;

        this.offset = new Vector2(-(width / 2), height / 2);
        this.text = text;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        font.setColor(getColor());
        font.draw(batch, text, getX() + offset.x, getY() + offset.y);
    }
}
