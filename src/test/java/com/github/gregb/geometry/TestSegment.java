package com.github.gregb.geometry;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.github.gregb.geometry.Box;
import com.github.gregb.geometry.Point;
import com.github.gregb.geometry.Segment;
import com.github.gregb.geometry.Vector;

public class TestSegment {

	Point p1 = new Point(-3, 4);
	Point p2 = new Point(-4, 3);
	Vector v1 = new Vector(-1, -1);
	Segment s1 = new Segment(Point.ORIGIN, p1);
	Segment s2 = new Segment(p1, p2);
	Segment s3 = new Segment(p2, p1);
	Segment s4 = p1.segmentTo(v1);
	Box b1 = new Box(Point.ORIGIN, p1);

	@Test
	public void magnitude() throws Exception {
		assertThat(s1.magnitude(), equalTo(5.0));
		assertThat(s2.magnitude(), equalTo(Math.sqrt(2)));
	}

	@Test
	public void equality() throws Exception {
		assertThat(s3.flip(), equalTo(s2));
		assertThat(s4, equalTo(s2));
	}

	@Test
	public void equivalence() throws Exception {
		assertThat(s3.flip(), equalTo(s2));
		assertThat(s4, equalTo(s2));
	}

	@Test
	public void boxConversion() throws Exception {
		assertThat(s1.asBox().normalize(), equalTo(b1));
	}
}
