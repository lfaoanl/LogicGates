package nl.faanveldhuijsen.logicgates.logics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class ConnectionPoints extends Array<Vector2> {

    public void last(Vector2 point) {
        set(lastIndex(), point);
    }

    public Vector2 last() {
        return get(lastIndex());
    }

    public void first(Vector2 point) {
        set(0, point);
    }

    private int lastIndex() {
        return size - 1;
    }

}
