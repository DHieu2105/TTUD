import edu.princeton.cs.algs4.Interval1D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LineSegment {
    static class Horizontal {
        Interval1D h ;
        int x1, x2, y;
        Horizontal(int x1, int x2, int y) {
            this.x1 = Math.min(x1, x2);
            this.x2 = Math.max(x1, x2);
            this.y = y;
        }
    }

    static class Vertical {
        int x, y1, y2;
        Vertical(int x, int y1, int y2) {
            this.x = x;
            this.y1 = Math.min(y1, y2);
            this.y2 = Math.max(y1, y2);
        }
    }

    static class Event implements Comparable<Event> {
        int x;
        int type;
        int y, y1, y2; // vertival

        Event(int x, int type, int y) {
            this.x = x;
            this.type = type;
            this.y = y;
        }

        Event(int x, int type, int y1, int y2) {
            this.x = x;
            this.type = type;
            this.y1 = y1;
            this.y2 = y2;
        }

        public int compareTo(Event that) {
            if (this.x != that.x) return this.x - that.x;
            return this.type - that.type;  // H_START < V_QUERY < H_END
        }
    }

    public static int countIntersections(List<Horizontal> horizontal, List<Vertical> vertical) {
        ArrayList<Event> events = new ArrayList<>();
        for (Horizontal h : horizontal) {
            events.add(new Event(h.x1, 0, h.y));           // H_START
            events.add(new Event(h.x2, 2, h.y));           // H_END
        }

        for (Vertical v : vertical) {
            events.add(new Event(v.x, 1, v.y1, v.y2));     // V_QUERY
        }
        Collections.sort(events);
        RangeSearch1D<Integer, Integer> bst = new RangeSearch1D<>();
        int count = 0;
        for (Event e : events) {
            if (e.type == 0) {
                bst.insert(e.y, e.y);
            } else if (e.type == 2) {
                // H_end
                if (bst.contains(e.y)) bst.delete(e.y);
            } else {
                for (int y : bst.rangeSearch2(e.y1, e.y2)) {
                    count++;
                }
            }
        }

        return count;
    }
    public static void main(String[] args) {
        List<Horizontal> H = new ArrayList<>();
        List<Vertical> V = new ArrayList<>();

        H.add(new Horizontal(1, 7, 3));
        H.add(new Horizontal(2, 5, 3));
        H.add(new Horizontal(0,3,2));
        V.add(new Vertical(1, 1, 3));
        V.add(new Vertical(4, 1, 5));

        int ans = countIntersections(H, V);
        StdOut.println("Number of intersections = " + ans);
    }
}
