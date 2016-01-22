package com.github.gregb.statistics;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;

public class Normal<T> extends CountingDistrubution<T> {

	public static enum OveragePolicy {
		IGNORE, CAP, RECHOOSE;
	}

	private final Random r = new Random();

	private final double min;
	private final double max;
	private final Function<Double, T> toBucket;
	private final OveragePolicy policy;

	final Set<Double> tooHigh = new HashSet<>();
	final Set<Double> tooLow = new HashSet<>();

	public Normal(double min, double max, OveragePolicy policy, Function<Double, T> toBucket) {
		this.min = min;
		this.max = max;
		this.toBucket = toBucket;
		this.policy = policy;
	}

	@Override
	public T get() {
		final double range = max + 1 - min;
		final double center = min + range / 2;
		final double scale = range / 6; // 3 standard deviations each way = 99.73% of all values

		// should leave (100-99.72)/2 = 0.28 / 2 = 0.14% each too high and too low

		final double mean0StdDev1 = r.nextGaussian();
		double inRange = mean0StdDev1 * scale + center;

		if (inRange < min) {
			tooLow.add(inRange);
			switch (this.policy) {
			case CAP:
				inRange = min;
				break;
			case RECHOOSE:
				return get();
			case IGNORE:
				// do nothing
			}

		}

		if (inRange > max + 1) {
			tooHigh.add(inRange);
			switch (this.policy) {
			case CAP:
				inRange = max;
				break;
			case RECHOOSE:
				return get();
			case IGNORE:
				// do nothing
			}
		}

		final T value = toBucket.apply(inRange);
		count(value);
		return value;
	}
}
