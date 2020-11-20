package nl.faanveldhuijsen.logicgates.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import nl.faanveldhuijsen.logicgates.figures.PixelFigure;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class ConnectionActor extends BaseActor {

    private PixelFigure pixelFigure;
    private final SwitchActor start;
    private SwitchActor end;
    private Vector2 tmpEnd;
//    private ArrayList<Vector2> positions = new ArrayList<>();

    public ConnectionActor(SwitchActor start, SwitchActor end) {
        super(0,0);

        this.start = start;
        this.end = end;

        pixelFigure = createFigure();

//        positions.add(getStartPosition());
//        positions.add(getEndPosition());

    }

    private PixelFigure createFigure() {
        PixelFigure pixelFigure = new PixelFigure();
        setSprite(pixelFigure.getTexture());
        pixelFigure.dispose();
        return pixelFigure;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        ShapeDrawer drawer = pixelFigure.getDrawer(batch);

        drawer.setColor(start.getOutputColor());
        drawer.line(getStartPosition(), getEndPosition());
    }

    @Override
    public void act(float delta) {
        super.act(delta);

    }

    private Vector2 getStartPosition() {
        return start.getPosition();
    }

    private Vector2 getEndPosition() {
        if (end == null) {
            return tmpEnd;
        }
        return end.getPosition();
    }

    public void setEnd(SwitchActor end) {
        this.end = end;
    }
    public void setEnd(float x, float y) {
        this.tmpEnd = new Vector2(x, y);
    }

    public void paint(float stageX, float stageY) {
    }

}
