package com.github.gregb.geometry;

import java.sql.SQLException;

// A Box is a rectangle on the 2D plane.
// A box's size and location is determined by two diagonally opposite corners.
// The normal form of a box is for the first corner to be at the upper right
// (coordinates with the largest X and Y values), and the second corner to be
// the at the lower left (as defined by a standard X/Y plane with values
// decreasing to the left and down).  Using the constructor function guarantees
// a normal box.
public class Box extends TwoPoints implements Shape {

	public Box(final Point a, final Point b) {
		// normalize
		super(new Point(Math.max(a.x, b.x), Math.max(a.y, b.y)), new Point(Math.min(a.x, b.x), Math.min(a.y, b.y)));
	}

	public Box(final TwoPoints tp) {
		super(tp);
	}

	public Box normalize() {
		final double xmin = Math.min(a.x, b.x);
		final double xmax = Math.max(a.x, b.x);

		final double ymin = Math.min(a.y, b.y);
		final double ymax = Math.max(a.y, b.y);

		return new Box(new Point(xmax, ymax), new Point(xmin, ymin));
	}

	@Override
	public boolean contains(final Point p) {
		// normalization ensures no need to check relative positions
		if (p.x > this.a.x) {
			return false;
		}

		if (p.x < this.b.x) {
			return false;
		}

		if (p.y > this.a.y) {
			return false;
		}

		if (p.y < this.b.y) {
			return false;
		}

		return true;
	}

	@Override
	public double area() {
		// normalization ensures these are positive
		final double dx = a.x - b.x;
		final double dy = a.y - b.y;
		return dx * dy;
	}

	@Override
	public double perimeter() {
		// normalization ensures these are positive
		final double dx = a.x - b.x;
		final double dy = a.y - b.y;
		return 2 * dx + 2 * dy;
	}

	public Segment asSegment() {
		return new Segment(this);
	}

	@Override
	public String getSQLTypeName() throws SQLException {
		return "box";
	}

}