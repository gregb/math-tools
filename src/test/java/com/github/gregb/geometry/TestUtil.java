package com.github.gregb.geometry;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.github.gregb.geometry.Point;
import com.github.gregb.geometry.Util;
import com.github.gregb.geometry.Vector;

public class TestUtil {

	private static final double ACCEPTABLE_DOUBLE_ERROR = 0.00000001;

	@Test
	public void interceptConvergence() throws Exception {
		final Point s1 = new Point(0, 0);
		final Point s2 = new Point(8, 0);
		final Vector v1 = new Vector(1, 1);
		final Vector v2 = new Vector(-1, 1);

		final double[] ta = Util.timeIntercept(s1, s2, v1, v2, 2);
		assertThat(ta[0], closeTo(3, ACCEPTABLE_DOUBLE_ERROR));
		assertThat(ta[1], closeTo(5, ACCEPTABLE_DOUBLE_ERROR));
	}

	@Test
	public void interceptDivergence() throws Exception {
		final Point s1 = new Point(0, 0);
		final Point s2 = new Point(8, 0);
		final Vector v1 = new Vector(1, 1);
		final Vector v2 = new Vector(-1, 1);

		// Starting at the same point, diverging and out of reach eventually
		// Theoretically they intercepted in the past
		final double[] tb = Util.timeIntercept(s1, s1, v2, v1, 2);
		assertThat(tb[0], closeTo(-1, ACCEPTABLE_DOUBLE_ERROR));
		assertThat(tb[1], closeTo(1, ACCEPTABLE_DOUBLE_ERROR));

		// Starting at different points, and diverging.
		// Theoretically they intercepted in the past,
		// and already diverged in the past
		// and will not come in range in the future
		final double[] tc = Util.timeIntercept(s1, s2, v2, v1, 2);
		assertThat(tc[0], closeTo(-5, ACCEPTABLE_DOUBLE_ERROR));
		assertThat(tc[1], closeTo(-3, ACCEPTABLE_DOUBLE_ERROR));
	}

	@Test
	public void interceptParallel() throws Exception {
		final Point s00 = new Point(0, 0);
		final Point s80 = new Point(8, 0);
		final Point s_2_4 = new Point(-2, -4);

		final Point s10 = new Point(1, 0);

		final Vector v11 = new Vector(1, 1);
		final Vector v22 = new Vector(2, 2);

		// Starting out of range, same velocity
		// Always out of range (in the past and future)
		final double[] td = Util.timeIntercept(s00, s80, v11, v11, 2);
		assertThat(td.length == 0, is(true));

		// Starting out of range, different velocity (but still parallel)
		// Always out of range (in the past and future)
		// [Slightly different code path than previous test]
		final double[] te = Util.timeIntercept(s00, s80, v11, v22, 2);
		assertThat(te.length == 0, is(true));

		// Starting in range, same velocity
		// Never out of range (past or future)
		final double[] tf = Util.timeIntercept(s00, s10, v11, v11, 2);
		assertThat(Double.isInfinite(tf[0]), is(true));
		assertThat(Double.isInfinite(tf[1]), is(true));

		// Starting out of range, different velocity (but still parallel)
		// Catches up, then outpaces
		final double[] tg = Util.timeIntercept(s00, s_2_4, v11, v22, 2);
		assertThat(tg[0], closeTo(2, ACCEPTABLE_DOUBLE_ERROR));
		assertThat(tg[1], closeTo(4, ACCEPTABLE_DOUBLE_ERROR));
	}

	public static boolean checkIntercept(Point s1, Point s2, Vector v1, Vector v2, double radius) {

		// get first intercept time
		final double[] t = Util.timeIntercept(s1, s2, v1, v2, radius);

		// calc locations of each point at t1
		final Point i1 = s1.translate(v1.scale(t[0]));
		final Point i2 = s2.translate(v2.scale(t[0]));

		// how far apart are they?
		final double calcR = i1.distanceTo(i2);

		// should be equal to radius
		// but allow for floating point weirdness
		if (Math.abs(calcR - radius) < 0.00001) {
			return true;
		}

		return false;
	}
}
