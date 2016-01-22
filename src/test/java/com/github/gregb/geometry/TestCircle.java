package com.github.gregb.geometry;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.github.gregb.geometry.Box;
import com.github.gregb.geometry.Circle;
import com.github.gregb.geometry.Point;

public class TestCircle {
	Point p1 = new Point(-3, 4);
	Point p2 = new Point(-4, 3);
	Point p3 = new Point(-1, -1);
	Point p4 = new Point(-8, -1);
	Point p5 = new Point(2, 9);
	Box b1 = new Box(p4, p5);
	Circle c1 = new Circle(p1, 5);

	@Test
	public void contains() throws Exception {
		assertThat(c1.contains(Point.ORIGIN), is(true));
		assertThat(c1.contains(p1), is(true));
		assertThat(c1.contains(p2), is(true));
		assertThat(c1.contains(p3), is(false));
	}

	@Test
	public void perimeterAndArea() throws Exception {
		assertThat(c1.area(), equalTo(Math.PI * 25));
		assertThat(c1.perimeter(), equalTo(Math.PI * 10));
	}

	@Test
	public void boxConversion() throws Exception {
		assertThat(c1.asBox(), equalTo(b1));
	}

}
