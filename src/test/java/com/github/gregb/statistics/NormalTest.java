package com.github.gregb.statistics;

import org.junit.Test;

import com.github.gregb.statistics.Normal;

public class NormalTest {

	@Test
	public void simpleToInt() throws Exception {
		final int n = 100000;

		final Normal<Integer> dist = new Normal<>(0, 10, Normal.OveragePolicy.CAP, d -> d.intValue());

		for (int i = 0; i < n; i++) {
			final Integer next = dist.get();
		}

		System.out.println(dist);

		System.out.println("Low  = " + dist.tooLow.size() + ": " + dist.tooLow.size() * 100.0 / n);
		System.out.println("High = " + dist.tooHigh.size() + ": " + dist.tooHigh.size() * 100.0 / n);
	}
}
