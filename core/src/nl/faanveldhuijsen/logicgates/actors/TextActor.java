package nl.faanveldhuijsen.logicgates.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector2;

public class TextActor extends BaseActor {

    private String text;
    private final BitmapFont font = new BitmapFont(Gdx.files.internal("fonts/Pixeled.fnt"));
    private Vector2 offset = new Vector2();

    public boolean centered = true;
    public float textWidth;
    public float textHeight;

    public TextActor(String text, Color color, float x, float y) {
        super(x, y);
        setText(text.toUpperCase());
        setColor(color);
    }

    public void setText(String text) {
        GlyphLayout layout = new GlyphLayout(font, text);
        textWidth = layout.width;
        textHeight = layout.height;

        if (centered) {
            this.offset = new Vector2(-(textWidth / 2), textHeight / 2);
        }
        this.text = text;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setTransparency(batch, parentAlpha);
        font.setColor(batch.getColor());
        font.draw(batch, text, getX() + offset.x, getY() + offset.y);
    }

    public String getText() {
        return text;
    }
}
