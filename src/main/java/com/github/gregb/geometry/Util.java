package com.github.gregb.geometry;

public class Util {

	private static final double[] NO_SOLUTIONS = new double[0];

	// timeIntercept computes the interception time of two moving points.
	// Two points, s1 and s2, which have respective velocities of v1 and v2, may
	// intercept at two times, returned by this function.  Interception is defined
	// as being with 'radius' distance of each other.  Go get a true intersection,
	// use a radius of zero.  Interception times may be in the past or future,
	// depending on starting locations and velocities.
	// If the velocities are parallel, two degenerate cases may arise.  Points may
	// never be in range, in which case NaN is returned for both time, or always
	// in range, in which case -Inf and +Inf are returned.
	public static double[] timeIntercept(Point s1, Point s2, Vector v1, Vector v2, double radius) {

		// locations, vector form
		// l1 = s1 + (t * v1)
		// l2 = s2 + (t * v2)

		// deltas in each dimension
		// dx = l2.x - l1.x
		// dy = l2.y - l1.y

		// range (distance equation)
		// r = sqrt (dx^2 + dy^2)
		// r^2 = dx^2 + dy^2
		// r^2 = (l2.x - l1.x)^2 + (l2.y - l1.y)^2
		// r^2 = ((s2.x + (t * v2.x)) - (s1.x + (t * v1.x)))^2 + ((s2.y + (t * v2.y)) - (s1.y + (t * v1.y)))^2

		// multiply that mess out, rearrange to the form
		// r^2 = (some big mess)t^2 + (another big mess)t + (a constant mess)
		// which is basically quadratic format
		// r^2 = at^2 +bt + c

		// coeffecients
		final double a = v2.y * v2.y - 2 * v1.y * v2.y + v2.x * v2.x - 2 * v1.x * v2.x + v1.y * v1.y + v1.x * v1.x;
		final double b = (2 * s2.y - 2 * s1.y) * v2.y + (2 * s2.x - 2 * s1.x) * v2.x + (2 * s1.y - 2 * s2.y) * v1.y + (2 * s1.x - 2 * s2.x) * v1.x;
		final double c = s2.y * s2.y - 2 * s1.y * s2.y + s2.x * s2.x - 2 * s1.x * s2.x + s1.y * s1.y + s1.x * s1.x;

		// if a==0 the end result will be a divide by zero, so just bail now
		if (a == 0) {
			// Geometric meaning: The points have the same velocities, so
			// they will either ALWAYS be within r of each other, or NEVER
			if (s1.distanceTo(s2) <= radius) {
				// All times are solutions
				// The points have been, and always will be, <= r from each other
				return new double[] { Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY };
			}

			// No times are solution
			return NO_SOLUTIONS;
		}

		// arrange that so that r^2 is just another part of the constant
		// at^2 +bt + c - r^2 = 0

		// then solve for t using known quadratic equation solution
		// t = (-b +/- sqrt(b^2 - 4a(c-r^2))) / 2a

		// but break into steps
		final double inner = b * b - 4 * a * (c - radius * radius);

		// will the discriminant be imaginary?
		if (inner < 0) {
			// there are no solutions; p1 and p2 are never within r of each other
			return NO_SOLUTIONS;
		}

		final double discr = Math.sqrt(inner);

		final double sol1 = (-b + discr) / (2 * a);
		final double sol2 = (-b - discr) / (2 * a);

		// return solutions in order
		if (sol1 < sol2) {
			return new double[] { sol1, sol2 };
		}

		if (sol2 < sol1) {
			return new double[] { sol2, sol1 };
		}

		// Only one solution
		return new double[] { sol1 };
	}

}
