package com.github.gregb.geometry;

import java.sql.SQLException;

// A Segment is a line segment defined by two points.
// It is represented in the postgres database by the <lseg> type.
public class Segment extends TwoPoints {

	public Segment(final Point a, final Point b) {
		super(a, b);
	}

	public Segment(final TwoPoints tp) {
		super(tp);
	}

	public Segment() {
		super();
	}

	// magnitude returns the length of the segment.
	public double magnitude() {
		return a.distanceTo(b);
	}

	// asBox returns the segment as a box whose corners are the segment's endpoints.
	public Box asBox() {
		return new Box(this);
	}

	// Flip returns a new segment whose endpoints are exchanged
	public Segment flip() {
		return new Segment(b, a);
	}

	@Override
	public String getSQLTypeName() throws SQLException {
		return "lseg";
	}

}
