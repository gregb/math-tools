package com.github.gregb.statistics;

import gnu.trove.list.array.TDoubleArrayList;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.function.Supplier;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class Histogram<T> extends CountingDistrubution<T> implements Supplier<T> {

	private final Map<T, Integer> weights = Maps.newHashMap();
	private long totalWeight = 0;
	private Random generator = new Random();
	private TDoubleArrayList bucketCutoffs;
	private ArrayList<T> buckets;

	public Histogram() {
		this.generator = new Random();
	}

	Histogram(long seed) {
		this.generator = new Random(seed);
	}

	public void addWeight(T t, int weight) {
		this.weights.put(t, weight);
		totalWeight += weight;
		buildBuckets();
	}

	@Override
	public T get() {
		final double r = generator.nextDouble();

		for (int bucket = 0; bucket < bucketCutoffs.size(); bucket++) {
			final double cutoff = bucketCutoffs.get(bucket);
			if (r <= cutoff) {
				final T t = buckets.get(bucket);
				count(t);
				return t;
			}
		}

		throw new RuntimeException("Random r = " + r + " did not match any cutoffs");
	}

	public double getExpected(T t) {
		return weights.get(t).doubleValue() / totalWeight;
	}

	public double getError() {
		return weights.size() / Math.sqrt(n);
	}

	public ArrayList<T> getN(long n) {
		final ArrayList<T> l = Lists.newArrayList();

		for (int i = 0; i < n; i++) {
			l.add(get());
		}

		return l;
	}

	private void buildBuckets() {
		this.bucketCutoffs = new TDoubleArrayList();
		this.buckets = Lists.newArrayList();
		resetCount();

		double cutoff = 0;
		int bucket = 0;

		for (final Entry<T, Integer> e : weights.entrySet()) {
			final double relativeWeight = e.getValue().doubleValue() / totalWeight;
			cutoff += relativeWeight;
			bucketCutoffs.add(cutoff);
			buckets.add(bucket, e.getKey());
			bucket++;
		}
	}

	public int size() {
		return weights.size();
	}

	public ArrayList<T> getBuckets() {
		return buckets;
	}

}
