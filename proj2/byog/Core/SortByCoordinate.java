package byog.Core;

import java.util.Comparator;

public class SortByCoordinate implements Comparator<Position> {

    @Override
    /**
     * 1 ==> o1 在 o2 左下方
     * 0 ==> o2 在 o1 左下方
     * */
    public int compare(Position o1, Position o2) {
        if (o1.x < o2.x) {
            return 1;
        } else if (o2.x < o1.x) {
            return 0;
        } else {
            if (o1.y < o2.y) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}
