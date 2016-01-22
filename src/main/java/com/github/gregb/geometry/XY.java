package com.github.gregb.geometry;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.sql.Struct;
import java.util.Map;

public abstract class XY implements SQLData, Struct {

	public double x;
	public double y;

	// Just for JDBC reconstitution
	protected XY() {
		this.x = 0;
		this.y = 0;
	}

	protected XY(final double x, final double y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ temp >>> 32);
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ temp >>> 32);
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
		final XY other = (XY) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x)) {
			return false;
		}
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y)) {
			return false;
		}
		return true;
	}

	@Override
	public void readSQL(final SQLInput stream, final String typeName) throws SQLException {
		this.x = stream.readDouble();
		this.y = stream.readDouble();
	}

	@Override
	public void writeSQL(final SQLOutput stream) throws SQLException {
		stream.writeDouble(x);
		stream.writeDouble(y);
	}

	@Override
	public String getSQLTypeName() throws SQLException {
		return "point";
	}

	@Override
	public Object[] getAttributes() throws SQLException {
		final Double[] d = new Double[2];
		d[0] = x;
		d[1] = y;
		return d;
	}

	@Override
	public Object[] getAttributes(final Map<String, Class<?>> map) throws SQLException {
		return getAttributes();
	}

}
