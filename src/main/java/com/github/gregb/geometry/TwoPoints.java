package com.github.gregb.geometry;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.sql.Struct;
import java.util.Map;

public abstract class TwoPoints implements Sized, SQLData, Struct {

	public final Point a;
	public final Point b;

	protected TwoPoints(final Point a, final Point b) {
		this.a = a;
		this.b = b;
	}

	protected TwoPoints(final TwoPoints tp) {
		this(tp.a, tp.b);
	}

	public TwoPoints() {
		this.a = new Point();
		this.b = new Point();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (a == null ? 0 : a.hashCode());
		result = prime * result + (b == null ? 0 : b.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final TwoPoints other = (TwoPoints) obj;
		if (a == null) {
			if (other.a != null) {
				return false;
			}
		} else if (!a.equals(other.a)) {
			return false;
		}
		if (b == null) {
			if (other.b != null) {
				return false;
			}
		} else if (!b.equals(other.b)) {
			return false;
		}
		return true;
	}

	public double width() {
		return b.x - a.x;
	}

	public double height() {
		return b.y - a.y;
	}

	@Override
	public Point getCenter() {
		return Point.centroid(a, b);
	}

	@Override
	public Object[] getAttributes() throws SQLException {
		return new Double[] { a.x, a.y, b.x, b.y };
	}

	@Override
	public Object[] getAttributes(final Map<String, Class<?>> map) throws SQLException {
		return getAttributes();
	}

	@Override
	public void readSQL(final SQLInput stream, final String typeName) throws SQLException {
		a.x = stream.readDouble();
		a.y = stream.readDouble();
		b.x = stream.readDouble();
		b.y = stream.readDouble();
	}

	@Override
	public void writeSQL(final SQLOutput stream) throws SQLException {
		stream.writeDouble(a.x);
		stream.writeDouble(a.y);
		stream.writeDouble(b.x);
		stream.writeDouble(b.y);
	}

}
