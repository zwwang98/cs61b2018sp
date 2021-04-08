package byog.Core;

import java.io.Serializable;
import java.util.Comparator;

public class Position implements Comparable, Serializable {
    int x;
    int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Comparator<Position> getCompByCoordinate() {
        Comparator comp = new Comparator<Position>() {
            @Override
            public int compare(Position s1, Position s2) {
                return s1.compareTo(s2);
            }
        };
        return comp;
    }

    public boolean equalsTo(Position p) {
        if (p == null) {
            return false;
        }
        return this.x == p.x && this.y == p.y;
    }

    @Override
    public int compareTo(Object o) {
        Position p = (Position) o;
        if (this.x == p.x) {
            return p.y - this.y;
        } else {
            return p.x - this.x;
        }
    }
}

