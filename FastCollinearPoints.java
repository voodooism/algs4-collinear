/* *****************************************************************************
 *  Name:    Just Me
 *  NetID:   voodooism
 *  Precept: P00
 *
 *  Description: Given a point p, the class determines whether p participates in
 *  a set of 4 or more collinear points.
 *  In each iteration of a given array of points it considers "p" as the origin.
 *  First, it sorts the points by nature order and then according to the slopes
 *  they make with "p". For each other points, determines the slope it makes
 *  with "p". Checks if any 3 (or more) adjacent points in the sorted order
 *  have equal slopes with respect to "p". If so, these points, together with p,
 *  are collinear and will be added to segments array.
 *
 *  Applying this method for each of the n points in turn yields an efficient
 *  algorithm to the problem. The algorithm solves the problem because points
 *  that have equal slopes with respect to p are collinear, and sorting brings
 *  such points together. The algorithm is fast because the bottleneck operation
 *  is sorting.
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private ArrayList<LineSegment> segments;
    private Point[] buffer;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        assertArrayIsValid(points);
        segments = new ArrayList<LineSegment>();
        buffer = points.clone();
        int numberOfPoints = points.length;

        for (Point p : points) {
            Arrays.sort(buffer);
            Arrays.sort(buffer, p.slopeOrder());
            for (int head = 1, tail = 2; tail < numberOfPoints; tail++) {
                while (tail < numberOfPoints && p.slopeTo(buffer[head]) == p
                        .slopeTo(buffer[tail])) {
                    tail++;
                }

                if ((tail - head >= 3) && p.compareTo(buffer[head]) < 0) {
                    segments.add(new LineSegment(p, buffer[tail - 1]));
                }
                head = tail;
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
