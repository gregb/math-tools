package com.github.gregb.geometry;

// A Shape is an enclosed 2D area.
// Circles, Boxes, and Polygons are all shapes.
public interface Shape extends Sized {
	public boolean contains(Point p);

	public double area();

	public double perimeter();
}
