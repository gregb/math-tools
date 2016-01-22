package com.github.gregb.geometry;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.github.gregb.geometry.Point;
import com.github.gregb.geometry.Vector;

public class TestPoint {

	final Point p2 = new Point(3, 4);
	final Point p3 = new Point(3, 4);
	final Point p4 = new Point(4, 5);

	final Vector v1 = new Vector(3, 4);
	final Vector v2 = new Vector(-3, -4);
	final Vector v3 = new Vector(1, 1);

	@Test
	public void pointsEqual() throws Exception {
		assertThat(p2, equalTo(p3));
	}

	@Test
	public void pointNotEqualToVector() throws Exception {
		assertThat(Point.ORIGIN, not(equalTo(Vector.ZERO)));
	}

	@Test
	public void distance() throws Exception {
		assertThat(Point.ORIGIN.distanceTo(p2), equalTo(5.0));
	}

	@Test
	public void translation() throws Exception {
		assertThat(Point.ORIGIN.translate(Vector.ZERO), equalTo(Point.ORIGIN));
		assertThat(p2.translate(v3), equalTo(p4));
	}

	@Test
	public void vectorCreation() throws Exception {
		assertThat(Point.ORIGIN.vectorTo(p2), equalTo(v1));
		assertThat(p2.vectorTo(Point.ORIGIN), equalTo(v2));
	}
}
