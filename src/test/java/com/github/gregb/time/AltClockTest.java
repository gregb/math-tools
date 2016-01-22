package com.github.gregb.time;

import static org.junit.Assert.*;

import java.time.Clock;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import org.junit.Before;
import org.junit.Test;

public class AltClockTest {

	private AltClock altClock;
	private final OffsetDateTime realStart = OffsetDateTime.of(2016, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC);
	private final OffsetDateTime altStart = OffsetDateTime.of(2000, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC);
	private final long nowOffsetMinutes = 5;
	private final Instant fakeNow = realStart.plusMinutes(nowOffsetMinutes).toInstant();

	private final int scale = 100;

	@Before
	public void setUp() throws Exception {
		this.altClock = new AltClock(realStart.toInstant(), altStart.toInstant(), scale);
		this.altClock.setRealClock(Clock.fixed(fakeNow, ZoneId.of("UTC")));
	}

	@Test
	public void testInstant() {
		final OffsetDateTime altTime = altStart.plusMinutes(nowOffsetMinutes * scale);
		final Instant expected = altTime.toInstant();
		assertEquals(expected, altClock.instant());
	}

	@Test
	public void testGetRealStart() {
		assertEquals(realStart.toInstant(), altClock.getRealStart());
	}

	@Test
	public void testGetSecondsFromAltEpoch() {
		assertEquals(this.altStart.toEpochSecond() + nowOffsetMinutes * 60 * scale, altClock.getSecondsFromAltEpoch());
	}

	@Test
	public void testGetRealOffsetSeconds() {
		assertEquals(this.fakeNow.getEpochSecond(), altClock.getRealOffsetSeconds());
	}

	@Test
	public void testGetRealSecondsElapsed() {
		assertEquals(0, altClock.getRealSecondsElapsed(0));
		assertEquals(5, altClock.getRealSecondsElapsed(500));
		assertEquals(1234567, altClock.getRealSecondsElapsed(123456789));
		assertEquals(-10000, altClock.getRealSecondsElapsed(-1000000));
	}

	@Test
	public void testGetAltSecondsElapsed() {
		assertEquals(0, altClock.getAltSecondsElapsed(0));
		assertEquals(500, altClock.getAltSecondsElapsed(5));
		assertEquals(123456700, altClock.getAltSecondsElapsed(1234567));
		assertEquals(-1000000, altClock.getAltSecondsElapsed(-10000));
	}

}
