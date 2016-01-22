package com.github.gregb.geometry;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.github.gregb.geometry.Box;
import com.github.gregb.geometry.Point;
import com.github.gregb.geometry.Segment;

public class TestBox {
	Point p1 = new Point(-2, 2);
	Point p2 = new Point(2, 2);
	Point p3 = new Point(2, -2);
	Point p4 = new Point(-2, -2);

	Point p5 = new Point(-3, 1);
	Point p6 = new Point(3, 1);
	Point p7 = new Point(3, -1);
	Point p8 = new Point(-3, -1);

	Box b1 = new Box(p1, p3);
	Box b2 = new Box(p3, p1);
	Box b3 = new Box(p2, p4);
	Box b4 = new Box(p4, p2); // canonical

	Segment s1 = new Segment(p4, p2);

	@Test
	public void canonical() throws Exception {
		assertThat(b1, equalTo(b4));
		assertThat(b2, equalTo(b4));
		assertThat(b3, equalTo(b4));
		assertThat(b4, equalTo(b4));
	}

	@Test
	public void perimeterAndArea() throws Exception {
		assertThat(b4.area(), equalTo(16.0));
		assertThat(b4.perimeter(), equalTo(16.0));
	}

	@Test
	public void contains() throws Exception {
		assertThat(b4.contains(p1), is(true));
		assertThat(b4.contains(p2), is(true));
		assertThat(b4.contains(p3), is(true));
		assertThat(b4.contains(p4), is(true));
		assertThat(b4.contains(Point.ORIGIN), is(true));

		assertThat(b4.contains(p5), is(false));
		assertThat(b4.contains(p6), is(false));
		assertThat(b4.contains(p7), is(false));
		assertThat(b4.contains(p8), is(false));
	}

	@Test
	public void segmentConversion() throws Exception {
		assertThat(b4.asSegment(), equalTo(s1.flip()));
	}
}
