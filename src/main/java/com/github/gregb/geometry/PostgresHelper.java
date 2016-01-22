package com.github.gregb.geometry;

import gnu.trove.list.array.TDoubleArrayList;

import java.util.Map;
import java.util.function.Function;

import com.google.common.collect.Maps;

public final class PostgresHelper {

	public static final Function<Point, String> FORMATTER_POINT = p -> {
		return String.format("(%.15f,%.15f)", p.x, p.y);
	};

	public static final Function<Vector, String> FORMATTER_VECTOR = p -> {
		return String.format("(%.15f,%.15f)", p.x, p.y);
	};

	public static final Function<Box, String> FORMATTER_BOX = b -> {
		return String.format("((%.15f,%.15f),(%.15f,%.15f))", b.a.x, b.a.y, b.b.x, b.b.y);
	};

	public static final Function<Segment, String> FORMATTER_SEGMENT = s -> {
		return String.format("[(%.15f,%.15f),(%.15f,%.15f)]", s.a.x, s.a.y, s.b.x, s.b.y);
	};

	public static final Function<Circle, String> FORMATTER_CIRCLE = c -> {
		return String.format("<(%.15f,%.15f),%.15f>", c.center.x, c.center.y, c.radius);
	};

	public static final Function<String, Point> PARSER_POINT = s -> {
		final double[] ds = parseDoubles(s);
		expectDoubles(ds, 2);
		return new Point(ds[0], ds[1]);
	};

	public static final Function<String, Vector> PARSER_VECTOR = s -> {
		final double[] ds = parseDoubles(s);
		expectDoubles(ds, 2);
		return new Vector(ds[0], ds[1]);
	};

	public static final Function<String, Box> PARSER_BOX = s -> {
		final double[] ds = parseDoubles(s);
		expectDoubles(ds, 4);
		return new Box(new Point(ds[0], ds[1]), new Point(ds[2], ds[3]));
	};

	public static final Function<String, Segment> PARSER_SEGEMENT = s -> {
		final double[] ds = parseDoubles(s);
		expectDoubles(ds, 4);
		return new Segment(new Point(ds[0], ds[1]), new Point(ds[2], ds[3]));
	};

	public static final Function<String, Circle> PARSER_CIRCLE = s -> {
		final double[] ds = parseDoubles(s);
		expectDoubles(ds, 3);
		return new Circle(new Point(ds[0], ds[1]), ds[2]);
	};

	private static final Map<Class<?>, Function<?, String>> GEOMETRY_TO_STRING = Maps.newHashMap();
	private static final Map<Class<?>, Function<String, ?>> STRING_TO_GEOMETRY = Maps.newHashMap();

	static {
		GEOMETRY_TO_STRING.put(Point.class, FORMATTER_POINT);
		GEOMETRY_TO_STRING.put(Vector.class, FORMATTER_POINT);
		GEOMETRY_TO_STRING.put(Segment.class, FORMATTER_SEGMENT);
		GEOMETRY_TO_STRING.put(Box.class, FORMATTER_BOX);
		GEOMETRY_TO_STRING.put(Circle.class, FORMATTER_CIRCLE);

		STRING_TO_GEOMETRY.put(Point.class, PARSER_POINT);
		STRING_TO_GEOMETRY.put(Vector.class, PARSER_VECTOR);
		STRING_TO_GEOMETRY.put(Segment.class, PARSER_SEGEMENT);
		STRING_TO_GEOMETRY.put(Box.class, PARSER_BOX);
		STRING_TO_GEOMETRY.put(Circle.class, PARSER_CIRCLE);
	}

	// Checks that the number of floats returned by the sql driver matches expectations.
	public static void expectDoubles(double[] ds, int expected) {

		// if positive, expect exactly that number
		if (expected > 0) {
			if (ds.length != expected) {
				throw new RuntimeException("Expected " + expected + " floats while parsing geometry, but got " + ds.length + " instead");
			}
		} else {
			// otherwise, any multiple of |expected| is ok
			// if expected == -1, then ANY amount is ok
			final int extra = ds.length % -expected;
			if (extra != 0) {
				throw new RuntimeException("Expected a multiple of " + -expected + " floats while parsing geometry, but got " + ds.length + " instead");
			}
		}

	}

	// ExtractFloats extracts all floats from a string
	// Parameter represents an ASCII string
	// Returns a slice of all floats parsed out
	// Returns an error if a float could not be parsed, plus all successfully parsed floats up until that point
	public static double[] parseDoubles(String s) {

		final TDoubleArrayList ds = new TDoubleArrayList();

		int start = 0;
		boolean inFloat = false;

		for (int i = 0; i < s.length(); i++) {
			final char b = s.charAt(i);

			// Float parts are 0 to 9, signs, and the decimal place
			final boolean isFloatPart = b == '.' || b == '+' || b == '-' || b >= '0' && b <= '9';

			if (isFloatPart && !inFloat) {
				// This char is the beginning of a float, mark it
				start = i;
				inFloat = true;
			}

			if (!isFloatPart && inFloat) {
				// The last char was the end of a float. parse it from where it started
				final String substr = s.substring(start, i);
				final double d = Double.parseDouble(substr);
				ds.add(d);
				inFloat = false;
			}
		}

		if (inFloat) {
			final String substr = s.substring(start, s.length() - 1);
			final double d = Double.parseDouble(substr);
			ds.add(d);
		}

		return ds.toArray();
	}

}
