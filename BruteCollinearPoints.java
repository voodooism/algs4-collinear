/* *****************************************************************************
 *  Name:    Just Me
 *  NetID:   voodooism
 *  Precept: P00
 *
 *  Description: Examines 4 points at a time and checks whether they all
 *  lie on the same line segment, returning all such line segments. To check
 *  whether the 4 points p, q, r, and s are collinear, checks whether the three
 *  slopes between p and q, between p and r, and between p and s are all equal.
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {

    private ArrayList<LineSegment> segments;
    private Point[] buffer;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        assertArrayIsValid(points);
        segments = new ArrayList<LineSegment>();
        int N = points.length;

        for (int p = 0; p < N; p++) {
            for (int q = p + 1; q < N; q++) {
                double pqSlope = buffer[p].slopeTo(buffer[q]);
                for (int r = q + 1; r < N; r++) {
                    double prSlope = buffer[p].slopeTo(buffer[r]);
                    if (Double.compare(pqSlope, prSlope) != 0) {
                        continue;
                    }
                    for (int s = r + 1; s < N; s++) {
                        double psSlope = buffer[p].slopeTo(buffer[s]);
                        if (Double.compare(pqSlope, psSlope) != 0) {
                            continue;
                        }
                        segments.add(new LineSegment(buffer[p], buffer[s]));
                    }
                }
            }
        }
    }

    /**
     * Checks whether any point in the array is null, or if the argument to the constructor contains
     * a repeated point.
     *
     * @param points points array
     */
    private void assertArrayIsValid(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }

        this.buffer = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }
            else {
                this.buffer[i] = points[i];
            }
        }
        Arrays.sort(this.buffer);
        for (int i = 1; i < buffer.length; i++) {
            if (buffer[i].compareTo(buffer[i - 1]) == 0) {
                throw new IllegalArgumentException();
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.size();
    }

    /**
     * Returns array consists of each line segment containing 4 points exactly once. If 4 points
     * appear on a line segment in the order p->q->r->s then the method returns array includes
     * either the line segment p->s or s->p (but not both) and doesn't include subsegments such as
     * p->r or q->r.
     */
    public LineSegment[] segments() {
        return this.segments.toArray(new LineSegment[segments.size()]);
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
