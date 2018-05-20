import java.util.Arrays;
import java.util.LinkedList;

public class FastCollinearPoints {
    private final LineSegment[] segments;

    public FastCollinearPoints(Point[] points)    // finds all line segments containing 4 points
    {
        if (points == null)
            throw new IllegalArgumentException("null array of points");
        for (Point point : points) if (point == null) throw new IllegalArgumentException("null point in array");
        Point[] ps = Arrays.copyOf(points, points.length);
        LinkedList<Line> lines = new LinkedList<>();
        for (int p = 0; p < ps.length; p++) {
            Arrays.sort(ps, points[p].slopeOrder());
            for (int q = 1; q < ps.length; ) {
                int seglen = 2;
                Point last = null;
                int r;
                double slopeToQ = ps[q].slopeTo(points[p]);
                if (slopeToQ == Double.NEGATIVE_INFINITY) throw new IllegalArgumentException("equal points in array");
                for (r = q + 1; r < ps.length && (Double.compare(ps[r].slopeTo(points[p]), slopeToQ) == 0); r++) {
                    last = ps[r];
                    seglen++;
                }
                if (seglen >= 4) {
                    Line seg = new Line(points[p], last);
                    if (!lines.contains(seg))
                        lines.add(seg);
                }
                q = r;
            }
        }
        segments = new LineSegment[lines.size()];
        int i = 0;
        for (Line line : lines) {
            segments[i++] = line.toLineSegment();
        }
    }

    public int numberOfSegments()        // the number of line segments
    {
        return segments.length;
    }

    public LineSegment[] segments()                // the line segments
    {
        return Arrays.copyOf(segments, segments.length);
    }

    private class Line {
        private final Point a, b;
        private final double slope;

        public Line(Point a, Point b) {
            this.a = a;
            this.b = b;
            this.slope = b.slopeTo(a);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Line) {
                Line other = (Line) obj;
                if (a.compareTo(other.a) == 0 && b.compareTo(other.b) == 0 || a.compareTo(other.b) == 0 && b.compareTo(other.a) == 0)
                    return true;
                if (Double.compare(slope, other.a.slopeTo(a)) == 0 || Double.compare(slope, other.b.slopeTo(a)) == 0)
                    return true;
            }
            return false;
        }

        public LineSegment toLineSegment() {
            return new LineSegment(a, b);
        }
    }
}
