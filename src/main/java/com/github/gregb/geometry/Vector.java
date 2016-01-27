package com.github.gregb.geometry;

import java.util.function.Function;

// Vector is a vector on the 2D plane.
// It is represented in the postgres database by the <point> type.
// Vectors are immutable.
public class Vector extends XY {

	// The Zero vector. [0,0]
	public static final Vector ZERO = new Vector(0, 0);

	// The X basis vector [1,0]
	public static final Vector BASIS_X = new Vector(1, 0);

	// The Y basis vector [0,1]
	public static final Vector BASIS_Y = new Vector(0, 1);

	public Vector() {
		// for jdbc and deserialization
	}

	public Vector(final double x, final double y) {
		super(x, y);
	}

	public Vector(final XY xy) {
		this(xy.x, xy.y);
	}

	public static final Vector polar(final double r, final double theta) {
		final double x = r * Math.cos(theta);
		final double y = r * Math.sin(theta);
		return new Vector(x, y);
	}

	// Magnitude returns the magnitude (length) of this vector.
	public double magnitude() {
		return Math.sqrt(x * x + y * y);
	}

	// Unit returns a new vector in the same direction as this vector, but with
	// a magnitude of 1.
	public Vector unit() {
		final double m = this.magnitude();
		return new Vector(this.x / m, this.y / m);
	}

	// Scale returns a new vector scaled by the multiplier n.
	public Vector scale(final double n) {
		return new Vector(this.x * n, this.y * n);
	}

	// Angle computes the angle of the vector
	public double angle() {
		return Math.atan2(this.y, this.x);
	}

	// Plus returns a new vector whose value is the sum of this vector, plus
	// all the vectors passed as parameters.
	public Vector plus(final Vector... vs) {
		double dx = this.x;
		double dy = this.y;

		for (final Vector v : vs) {
			dx += v.x;
			dy += v.y;
		}

		return new Vector(dx, dy);
	}

	// Dot returns the dot product of the vector with another vector
	public double dot(final Vector v) {
		return this.x * v.x + this.y * v.y;
	}

	// CrossZ returns the Z component of the cross product of this vector with
	// another vector. Because the inputs are 2D vectors in the X/Y plane, and
	// their implied Z components are zero, their cross product is colinear with
	// the Z axis (X and Y components being zero), and not representable by a
	// Vector in this package. This method returns only the Z component, whose
	// direction can be determined by its sign.
	public double crossZ(final Vector v) {
		return this.x * v.y - this.y * v.x;
	}

	// AsSegment returns a segment from the origin to the vector endpoint.
	// To obtain a segment from a point other than the origin, use
	// Point.AsSegment()
	public Segment asSegment() {
		return new Segment(Point.ORIGIN, new Point(this));
	}

	public Vector ofMagnitude(final double newMagnitude) {
		final double m = this.magnitude();
		return new Vector(this.x * newMagnitude / m, this.y * newMagnitude / m);
	}

	public static final Function<Vector, String> FORMATTER_COMMON = p -> {
		return String.format("<%.15f,%.15f>", p.x, p.y);
	};

	@Override
	public String toString() {
		return FORMATTER_COMMON.apply(this);
	}

}
