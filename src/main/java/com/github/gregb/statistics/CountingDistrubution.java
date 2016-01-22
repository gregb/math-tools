package com.github.gregb.statistics;

import java.util.Map;
import java.util.TreeMap;
import java.util.function.Supplier;

public abstract class CountingDistrubution<T> implements Supplier<T> {

	protected Map<T, Long> counter = new TreeMap<>();
	protected long n;

	public double getActual(T t) {
		return counter.get(t).doubleValue() / n;
	}

	public Long getCount(T t) {
		return counter.get(t);
	}

	public void resetCount() {
		this.counter.clear();
		this.n = 0;
	}

	protected void count(T t) {
		Long c = counter.get(t);
		if (c == null) {
			c = new Long(1);
		} else {
			c++;
		}

		n++;
		this.counter.put(t, c);
	}

	@Override
	public String toString() {
		return "CountingDistrubution[n = " + n + "]\n" + counter;

	}

}
