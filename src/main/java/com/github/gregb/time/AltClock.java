package com.github.gregb.time;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

public class AltClock extends Clock {

	public static final ZoneId UTC = ZoneId.of("UTC");

	private final Instant realStart;
	private final Instant altStart;
	private final long realStartSeconds;
	private final long altStartSeconds;
	private final int scale;
	private Clock refClock;
	private final ZoneId timeZone = UTC;

	public AltClock(final Instant realStart, final Instant altStart, final int scale) {
		this.realStart = realStart;
		this.realStartSeconds = realStart.getEpochSecond();
		this.altStart = altStart;
		this.altStartSeconds = altStart.getEpochSecond();
		this.scale = scale;
		this.refClock = Clock.system(timeZone);
	}

	@Override
	public ZoneId getZone() {
		return timeZone;
	}

	@Override
	public Clock withZone(final ZoneId zone) {
		return refClock.withZone(zone);
	}

	@Override
	public Instant instant() {
		return Instant.ofEpochSecond(getSecondsFromAltEpoch());
	}

	// For unit testing
	void setRealClock(final Clock realClock) {
		this.refClock = realClock;
	}

	public Instant getRealStart() {
		return realStart;
	}

	public Instant getAltStart() {
		return altStart;
	}

	public long getSecondsFromRealEpoch() {
		return this.refClock.withZone(timeZone).millis() / 1000;
	}

	public long getSecondsFromAltEpoch() {
		final long secondsFromRealEpoch = this.refClock.withZone(timeZone).millis() / 1000;
		final long realSecondsElapsed = secondsFromRealEpoch - realStartSeconds;
		final long altSecondsElapsed = realSecondsElapsed * scale;
		final long secondsFromAltEpoch = altSecondsElapsed + this.altStartSeconds;
		return secondsFromAltEpoch;
	}

	public long getSecondsFromAltEpoch(final long secondsFromRealEpoch) {
		final long realSecondsElapsed = secondsFromRealEpoch - realStartSeconds;
		final long altSecondsElapsed = realSecondsElapsed * scale;
		final long secondsFromAltEpoch = altSecondsElapsed + this.altStartSeconds;
		return secondsFromAltEpoch;
	}

	public long getSecondsFromRealEpoch(final long secondsFromAltEpoch) {
		final long altSecondsElapsed = secondsFromAltEpoch - altStartSeconds;
		final long realSecondsElapsed = altSecondsElapsed / scale;
		final long secondsFromRealEpoch = realSecondsElapsed + this.realStartSeconds;
		return secondsFromRealEpoch;
	}

	public long getRealOffsetSeconds() {
		return this.refClock.withZone(timeZone).millis() / 1000;
	}

	public long getRealSecondsElapsed(final long altSecondsElapsed) {
		return altSecondsElapsed / scale;
	}

	public long getAltSecondsElapsed(final long realSecondsElapsed) {
		return realSecondsElapsed * scale;
	}

	public Instant getRealInstant(final Instant altTime) {
		return Instant.ofEpochSecond(getSecondsFromRealEpoch(altTime.getEpochSecond()));
	}

	public Instant getAltInstant(final Instant realTime) {
		return Instant.ofEpochSecond(getSecondsFromAltEpoch(realTime.getEpochSecond()));
	}

}
