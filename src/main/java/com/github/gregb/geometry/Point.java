package com.github.gregb.geometry;

import java.sql.SQLException;
import java.util.Collection;

// Point is a point on the 2D plane.
// It is represented in the postgres database by the <point> type.
// Points are immutable.
public class Point extends XY {

	// The origin is the zero point on the 2D plane (0,0)
	public static Point ORIGIN = new Point(0, 0);

	// Just for JDBC reconstitution
	public Point() {
		super();
	}

	public Point(final double x, final double y) {
		super(x, y);
	}

	public Point(final XY xy) {
		this(xy.x, xy.y);
	}

	public static final Point polar(final double r, final double theta) {
		final double x = r * Math.cos(theta);
		final double y = r * Math.sin(theta);
		return new Point(x, y);
	}

	public static final Point centroid(final Point... points) {
		double sumX = 0;
		double sumY = 0;

		for (final Point p : points) {
			sumX += p.x;
			sumY += p.y;
		}

		return new Point(sumX / points.length, sumY / points.length);
	}

	public static final Point centroid(final Collection<Point> points) {
		double sumX = 0;
		double sumY = 0;

		for (final Point p : points) {
			sumX += p.x;
			sumY += p.y;
		}

		return new Point(sumX / points.size(), sumY / points.size());
	}

	public static double distanceBetween(final Point p1, final Point p2) {
		final double dx = p2.x - p1.x;
		final double dy = p2.y - p1.y;
		return Math.sqrt(dx * dx + dy * dy);
	}

	// DistanceTo returns the euclidean distance between this point and another.
	public double distanceTo(final Point other) {
		return distanceBetween(this, other);
	}

	// VectorTo computes a vector pointing from this point to another point.
	public Vector vectorTo(final Point other) {
		final double dx = other.x - this.x;
		final double dy = other.y - this.y;
		return new Vector(dx, dy);
	}

	// Translate returns a new point translated by the given vector.
	public Point translate(final Vector v) {
		return new Point(this.x + v.x, this.y + v.y);
	}

	// segmentTo returns a segment from this point to a point as translated by
	// the
	// given vector.
	public Segment segmentTo(final Vector v) {
		return new Segment(this, this.translate(v));
	}

	@Override
	public String getSQLTypeName() throws SQLException {
		return "point";
	}

}
