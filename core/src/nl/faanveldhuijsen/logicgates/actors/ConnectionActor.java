package nl.faanveldhuijsen.logicgates.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import nl.faanveldhuijsen.logicgates.figures.PixelFigure;
import space.earlygrey.shapedrawer.JoinType;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class ConnectionActor extends BaseActor {

    private PixelFigure pixelFigure;
    private SwitchActor start;
    private SwitchActor end;
    private Array<Vector2> path = new Array<>();

    private enum Direction {
            HORIZONTAL,VERTICAL;
    }
    private Direction direction = Direction.HORIZONTAL;

    public ConnectionActor(SwitchActor start, SwitchActor end) {
        super(0,0);

        this.start = start;
        this.end = end;

        pixelFigure = createFigure();

//        positions.add(getStartPosition());
//        positions.add(new Vector2(getStartPosition().x + 40, getStartPosition().y));
//        positions.add(new Vector2(getStartPosition().x + 40, getEndPosition().y));

//        positions.add(getEndPosition());
    }

    public ConnectionActor(SwitchActor start) {
        super(0,0);

        this.start = start;

        pixelFigure = createFigure();

    }

    private PixelFigure createFigure() {
        PixelFigure pixelFigure = new PixelFigure();
        setSprite(pixelFigure.getTexture(), getWidth(), getHeight());
        pixelFigure.dispose();
        return pixelFigure;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        ShapeDrawer drawer = pixelFigure.getDrawer(batch);

//        Color outputColor = new Color(start.getOutputColor());
//        outputColor.a = 0.7f;
        drawer.setColor(start.getOutputColor());

        drawer.path(getPositions(), 4, JoinType.SMOOTH, true);
    }

    public void paint(float x, float y) {
        Vector2 point = new Vector2(x, y);
        if (path.size == 0) {
            point.y = (Math.round(getStartPosition().y / 16) * 16) + getStartPosition().y % 16;
            path.add(point);
            return;
        }

        if (nearLastPoint(point) && path.size >= 3) {
            direction = direction == Direction.HORIZONTAL ? Direction.VERTICAL : Direction.HORIZONTAL;
            path.pop();
        }

        if (switchDirection(point)) {
            point = normalize(point);
            path.add(point);
            return;
        }
        point = normalize(point);

        path.set(path.size - 1, point);
    }

    public void snapTo(Vector2 position) {
        if (path.size >= 2) {
            if (direction == Direction.HORIZONTAL) {
                path.get(path.size - 2).y = position.y;
            } else {
                path.get(path.size - 2).x = position.x;
            }
        }
        path.set(path.size - 1, position);
    }

    private Array<Vector2> getPositions() {
        Array<Vector2> points = new Array<>();
        points.add(getStartPosition());
        for (Vector2 pos : path) {
            points.add(pos);
        }
        points.add(getEndPosition());
        return points;
    }

    private Vector2 getStartPosition() {
        return start.getPosition();
    }

    private Vector2 getEndPosition() {
        if (end == null) {
            return path.get(path.size - 1);
        }
        return end.getPosition();
    }

    private boolean nearLastPoint(Vector2 point) {
        return point.dst(path.get(path.size - 1)) < 4;
    }

    private Vector2 normalize(Vector2 point) {
        if (direction == Direction.HORIZONTAL) {
            point.y = path.get(path.size - 1).y;
//            point.y = Math.round(point.y / 16) * 16;
        } else {
            point.x = path.get(path.size - 1).x;
//            point.x = Math.round(point.x / 16) * 16;
        }
        return point;
    }

    private boolean switchDirection(Vector2 point) {

        if (direction == Direction.HORIZONTAL && Math.abs(path.get(path.size - 1).y - point.y) > 32) {
            direction = Direction.VERTICAL;
            return true;
        }
        if (direction == Direction.VERTICAL && Math.abs(path.get(path.size - 1).x - point.x) > 32) {
            direction = Direction.HORIZONTAL;
            return true;
        }

        return false;
    }

    public Array<Vector2> getPath() {
        return path;
    }

    public void setPath(Array<Vector2> path) {
        this.path = path;
    }

}
