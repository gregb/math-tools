package com.github.gregb.statistics;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.github.gregb.statistics.Histogram;

public class HistogramTest {

	private static final double CLOSE_ENOUGH = 0.00000001;

	@Test
	public void twoItemEvenWeight() throws Exception {
		final Histogram<String> h = new Histogram<String>();
		h.addWeight("A", 1);
		h.addWeight("B", 1);

		statisticsTest(100L, h);
		statisticsTest(1000L, h);
		statisticsTest(10000L, h);
		statisticsTest(100000L, h);
	}

	@Test
	public void elevenItemEvenWeight() throws Exception {
		final Histogram<String> h = new Histogram<String>();
		h.addWeight("A", 1);
		h.addWeight("B", 1);
		h.addWeight("C", 1);
		h.addWeight("D", 1);
		h.addWeight("E", 1);
		h.addWeight("F", 1);
		h.addWeight("G", 1);
		h.addWeight("H", 1);
		h.addWeight("I", 1);
		h.addWeight("J", 1);
		h.addWeight("K", 1);

		statisticsTest(100L, h);
		statisticsTest(1000L, h);
		statisticsTest(10000L, h);
		statisticsTest(100000L, h);
	}

	@Test
	public void fourItemUnqualWeight() throws Exception {
		final Histogram<String> h = new Histogram<String>();
		h.addWeight("A", 1);
		h.addWeight("B", 2);
		h.addWeight("C", 3);
		h.addWeight("D", 4);

		statisticsTest(100L, h);
		statisticsTest(1000L, h);
		statisticsTest(10000L, h);
		statisticsTest(100000L, h);
	}

	@Test
	public void threeReallyUnevenItems() throws Exception {
		final Histogram<String> h = new Histogram<String>();
		h.addWeight("A", 1);
		h.addWeight("B", 95);
		h.addWeight("C", 4);

		statisticsTest(100L, h);
		statisticsTest(1000L, h);
		statisticsTest(10000L, h);
		statisticsTest(100000L, h);
	}

	private <T> void statisticsTest(long n, Histogram<T> h) {

		h.resetCount();
		h.getN(n);

		final long[] counts = new long[h.size()];
		final double[] actual = new double[h.size()];
		final double[] expected = new double[h.size()];
		long sumCounts = 0;
		double sumExpected = 0;
		double sumActual = 0;

		int i = 0;

		for (final T b : h.getBuckets()) {
			counts[i] = h.getCount(b);
			actual[i] = h.getActual(b);
			expected[i] = h.getExpected(b);

			sumCounts += counts[i];
			sumExpected += expected[i];
			sumActual += actual[i];

			assertThat(actual[i], closeTo(expected[i], h.getError()));
			i++;
		}

		assertThat(sumCounts, equalTo(n));
		assertThat(sumExpected, closeTo(1.0, CLOSE_ENOUGH));
		assertThat(sumActual, closeTo(1.0, CLOSE_ENOUGH));

	}

}
