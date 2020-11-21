package nl.faanveldhuijsen.logicgates.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import nl.faanveldhuijsen.logicgates.figures.PixelFigure;
import space.earlygrey.shapedrawer.JoinType;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class ConnectionActor extends BaseActor {

    private Vector2 previousStart;
    private PixelFigure pixelFigure;
    private SwitchActor start;
    private SwitchActor end;
    private Array<Vector2> path = new Array<>();

    private final BitmapFont font = new BitmapFont();


    private enum Direction {
            HORIZONTAL,VERTICAL;
    }
    private Direction direction = Direction.HORIZONTAL;

    public ConnectionActor(SwitchActor start, SwitchActor end) {
        super(0,0);

        this.start = start;
        this.end = end;

        this.previousStart = getStartPosition();

        pixelFigure = createFigure();

//        positions.add(getStartPosition());
//        positions.add(new Vector2(getStartPosition().x + 40, getStartPosition().y));
//        positions.add(new Vector2(getStartPosition().x + 40, getEndPosition().y));

//        positions.add(getEndPosition());
    }

    public ConnectionActor(SwitchActor start) {
        super(0,0);

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
            snapTo(previousStart, true);
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
        if (path.size == 0) {
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


        path.set(path.size - 1, point);
    }

    public void snapTo(Vector2 position) {
        snapTo(position, false);
    }
    public void snapTo(Vector2 position, boolean begin) {
        Vector2 movable;
        if (begin) {
            movable = path.get(1);
        } else {
            if (path.size <= 2) {
                Vector2 lastPoint = path.pop();
                Vector2 newPoint1 = new Vector2(position.x - 50, getStartPosition().y);
                Vector2 newPoint2 = new Vector2(position.x - 50, position.y);
                path.add(newPoint1);
                path.add(newPoint2);
                path.add(lastPoint);
                return;
            }
            movable = path.get(path.size - 2);
        }

        if (movable != null && (path.size >= 2 || begin) ) {
            if (direction == Direction.HORIZONTAL) {
                movable.y = position.y;
            } else {
                movable.x = position.x;
            }
        }

        if (begin) {
            path.set(0, position);
        } else {
            path.set(path.size - 1, position);
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
            return path.get(path.size - 1);
        }
        return end.getPosition();
    }

    private boolean nearLastPoint(Vector2 point) {
        return point.dst(path.get(path.size - 1)) < 8;
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
