package com.github.gregb.geometry;

// Circle is a circle on the 2D plane.
// It is represented in the postgres database by the <circle> type.
// Implements the Shape interface.
public class Circle implements Shape {

	public static Circle UNIT = new Circle(Point.ORIGIN, 1);

	public Circle(Point center, double radius) {
		this.center = center;
		this.radius = radius;
	}

	public final Point center;
	public final double radius;

	@Override
	public boolean contains(Point p) {
		return this.center.distanceTo(p) <= this.radius;
	}

	@Override
	public double area() {
		return Math.PI * this.radius * this.radius;
	}

	@Override
	public double perimeter() {
		return 2 * Math.PI * this.radius;
	}

	// asBox returns a box which exactly encloses the circle.
	// The box is tangent to the circle at its sides' midpoints, and shares
	// a center with the circle.
	public Box asBox() {
		final Point ll = new Point(this.center.x - this.radius, this.center.y - this.radius);
		final Point ur = new Point(this.center.x + this.radius, this.center.y + this.radius);
		return new Box(ll, ur);
	}

	@Override
	public Point getCenter() {
		return center;
	}
}
