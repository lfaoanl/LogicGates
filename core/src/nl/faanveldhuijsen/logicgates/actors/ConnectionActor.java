package nl.faanveldhuijsen.logicgates.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import nl.faanveldhuijsen.logicgates.figures.PixelFigure;
import nl.faanveldhuijsen.logicgates.logics.ConnectionPoints;
import space.earlygrey.shapedrawer.JoinType;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class ConnectionActor extends BaseActor {

    private Vector2 previousStart;
    private PixelFigure pixelFigure;
    private SwitchActor start;
    private SwitchActor end;
    private ConnectionPoints path = new ConnectionPoints();

    private final BitmapFont font = new BitmapFont();


    private enum Direction {
        HORIZONTAL, VERTICAL;
    }

    private Direction direction = Direction.HORIZONTAL;

    public ConnectionActor(SwitchActor start, SwitchActor end) {
        super(0, 0);

        this.start = start;
        this.end = end;

        this.previousStart = getStartPosition();

        pixelFigure = createFigure();
    }

    public ConnectionActor(SwitchActor start) {
        super(0, 0);

        this.start = start;
        this.previousStart = getStartPosition();

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

        if (!getStartPosition().equals(previousStart)) {
//            snapTo(previousStart, true);
            previousStart = getStartPosition();
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        ShapeDrawer drawer = pixelFigure.getDrawer(batch);

//        Color outputColor = new Color(start.getOutputColor());
//        outputColor.a = 0.7f;
        drawer.setColor(start.getOutputColor());

        Array<Vector2> positions = getPositions();

        drawer.path(positions, 4, JoinType.SMOOTH, true);
        font.draw(batch, "start", positions.first().x, positions.first().y);
        for (int i = 0; i < positions.size; i++) {
            drawer.filledCircle(positions.get(i), 6);
        }
        font.draw(batch, "end", getEndPosition().x, getEndPosition().y);
    }

    public void paint(float x, float y) {
        Vector2 point = new Vector2(x, y);
        if (path.isEmpty()) {
            point.y = (Math.round(getStartPosition().y / 16) * 16) + getStartPosition().y % 16;
            path.add(point);
            return;
        }

        if (nearLastPoint(point) && path.size >= 2) {
            direction = direction == Direction.HORIZONTAL ? Direction.VERTICAL : Direction.HORIZONTAL;
            path.pop();
        }

        if (switchDirection(point)) {
            point = normalize(point);
            path.add(point);
            return;
        }
        point = normalize(point);

        path.last(point);
    }

    public void snapTo(Vector2 position) {
        snapTo(position, false);
    }

    public void snapTo(Vector2 position, boolean begin) {
        Vector2 movable;
        if (begin) {
            movable = path.get(1);
        } else {
            if (path.size <= 1) {
                path.pop();
                float x = getStartPosition().x + ((position.x - getStartPosition().x) / 2);
                Vector2 newPoint1 = new Vector2(x, getStartPosition().y);
                Vector2 newPoint2 = new Vector2(x, position.y);
                path.add(newPoint1);
                path.add(newPoint2);
                path.add(position);
                return;
            }
            movable = path.get(path.size - 2);
        }

        if (movable != null && (path.size >= 2 || begin)) {
            if (direction == Direction.HORIZONTAL) {
                movable.y = position.y;
            } else {
                movable.x = position.x;
            }
        }

        if (begin) {
            path.first(position);
        } else {
            path.last(position);
        }
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
            return path.last();
        }
        return end.getPosition();
    }

    private boolean nearLastPoint(Vector2 point) {
        return point.dst(path.last()) < 8;
    }

    private Vector2 normalize(Vector2 point) {
        if (direction == Direction.HORIZONTAL) {
            point.y = path.last().y;
        } else {
            point.x = path.last().x;
        }
        return point;
    }

    private boolean switchDirection(Vector2 point) {

        if (direction == Direction.HORIZONTAL && Math.abs(path.last().y - point.y) > 32) {
            direction = Direction.VERTICAL;
            return true;
        }
        if (direction == Direction.VERTICAL && Math.abs(path.last().x - point.x) > 32) {
            direction = Direction.HORIZONTAL;
            return true;
        }

        return false;
    }

    public ConnectionPoints getPath() {
        return path;
    }

    public void setPath(ConnectionPoints path) {
        this.path = path;
    }

}
