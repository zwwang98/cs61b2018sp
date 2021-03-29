package byog.Core;

import java.util.Comparator;

public class Position implements Comparable {
    int x;
    int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Comparator<Position> getCompByCoordinate()
    {
        Comparator comp = new Comparator<Position>(){
            @Override
            public int compare(Position s1, Position s2)
            {
                return s1.compareTo(s2);
            }
        };
        return comp;
    }

    @Override
    public int compareTo(Object o) {
        Position p = (Position) o;
        int x = p.x;
        int y = p.y;
        if (this.x == x) {
            return y - this.y;
        } else {
            return x - this.x;
        }
    }
}

