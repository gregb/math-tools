package com.github.gregb.geometry;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.number.IsCloseTo.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class TestVector {

	private static final double ACCEPTABLE_DOUBLE_ERROR = 0.00000001;

	Vector v1 = new Vector(3, 4);
	Vector v2 = new Vector(-3, -4);
	Vector v3 = new Vector(1, 1);
	Vector v4 = new Vector(2, 3);

	@Test
	public void scaling() throws Exception {
		assertThat(v1.scale(-1), equalTo(v2));
		assertThat(Vector.BASIS_Y.scale(1), equalTo(Vector.BASIS_Y));
	}

	@Test
	public void magnitude() throws Exception {
		assertThat(Vector.ZERO.magnitude(), equalTo(0.0));
		assertThat(v1.magnitude(), equalTo(5.0));
		assertThat(v2.magnitude(), equalTo(5.0));
		assertThat(v3.magnitude(), equalTo(Math.sqrt(2)));
	}

	@Test
	public void angle() throws Exception {
		assertThat(Vector.BASIS_X.angle(), equalTo(0.0));
		assertThat(Vector.BASIS_Y.angle(), equalTo(Math.PI / 2));
		assertThat(v3.angle(), equalTo(Math.PI / 4));
	}

	@Test
	public void unitVector() throws Exception {
		assertThat(Vector.BASIS_X.unit(), equalTo(Vector.BASIS_X));
		final Vector v1Unit = v1.unit();
		assertThat(v1Unit.magnitude(), equalTo(1.0));
		assertThat(v1Unit.angle(), closeTo(v1.angle(), ACCEPTABLE_DOUBLE_ERROR));
	}

	@Test
	public void ofMagnitude() throws Exception {

		final Vector v = new Vector(3, 4);
		assertEquals(5, v.magnitude(), ACCEPTABLE_DOUBLE_ERROR);

		final Vector v10 = v.ofMagnitude(10);
		assertEquals(10, v10.magnitude(), ACCEPTABLE_DOUBLE_ERROR);
		assertEquals(v.angle(), v10.angle(), ACCEPTABLE_DOUBLE_ERROR);
		assertEquals(6, v10.x, ACCEPTABLE_DOUBLE_ERROR);
		assertEquals(8, v10.y, ACCEPTABLE_DOUBLE_ERROR);
	}

	@Test
	public void addition() throws Exception {
		assertThat(v1.plus(v2), equalTo(Vector.ZERO));
		assertThat(Vector.ZERO.plus(v3, Vector.BASIS_X, Vector.BASIS_Y, Vector.BASIS_X, Vector.BASIS_Y, Vector.BASIS_Y), equalTo(v1));
	}

	@Test
	public void dotProduct() throws Exception {
		assertThat(v1.dot(v2), equalTo(-25.0));
		assertThat(v1.dot(v3), equalTo(7.0));
		assertThat(v1.dot(v4), equalTo(18.0));
	}

	@Test
	public void crossProduct() throws Exception {
		assertThat(v1.crossZ(v2), equalTo(0.0));
		assertThat(v1.crossZ(v3), equalTo(-1.0));
		assertThat(v1.crossZ(v4), equalTo(1.0));

	}

	@Test
	public void segmentConversion() throws Exception {
		final Segment s = new Segment(Point.ORIGIN, new Point(2, 3));
		assertThat(v4.asSegment(), equalTo(s));
	}
}