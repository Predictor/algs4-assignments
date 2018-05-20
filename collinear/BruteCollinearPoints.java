import java.util.Arrays;
import java.util.LinkedList;

public class BruteCollinearPoints {
    private final LinkedList<LineSegment> segments;

    public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
    {
        if (points == null)
            throw new IllegalArgumentException("null array of points");
        for (Point point : points) if (point == null) throw new IllegalArgumentException("null point in array");
        Point[] pts = Arrays.copyOf(points, points.length);
        Arrays.sort(pts);
        for (int i = 0; i < pts.length - 1; i++)
            if (pts[i].compareTo(pts[i + 1]) == 0) throw new IllegalArgumentException("equal points in array");
        segments = new LinkedList<LineSegment>();
        for (int p = 0; p < pts.length; p++)
            for (int q = p + 1; q < pts.length; q++)
                for (int r = q + 1; r < pts.length; r++)
                    for (int s = r + 1; s < pts.length; s++)
                        if (Double.compare(pts[p].slopeTo(pts[q]), pts[q].slopeTo(pts[r])) == 0 &&
                                Double.compare(pts[q].slopeTo(pts[r]), pts[r].slopeTo(pts[s])) == 0) {
                            segments.add(new LineSegment(pts[p], pts[s]));
                        }
    }

    public int numberOfSegments()        // the number of line segments
    {
        return segments.size();
    }

    public LineSegment[] segments()                // the line segments
    {
        return segments.toArray(new LineSegment[0]);
    }
}