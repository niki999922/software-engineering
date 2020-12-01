package com.kochetkov.stats.event

import com.kochetkov.stats.clock.SettableClock
import org.junit.Assert
import org.junit.Test
import java.time.Instant
import kotlin.math.abs


class ClockEventsStatisticsTest {
    private fun equals(left: Double, right: Double): Boolean {
        return abs(left - right) <= EPSILON
    }

    private fun prepare() = ClockEventsStatistics(SettableClock(Instant.ofEpochSecond(0L))) to "incident"

    @Test
    fun testClockEventsStatisticsFixedTimeNoEvents() {
        val (statsManager, eventName) = prepare()

        Assert.assertTrue(equals(statsManager.getEventStatisticsByName(eventName), 0.0))
        Assert.assertEquals(0, statsManager.getAllEventStatistics().size.toLong())
    }

    @Test
    fun testClockEventsStatisticsFixedTimeOneEventStatsForNonExisting() {
        val (statsManager, eventName) = prepare()
        val anotherEventName = "another event"
        statsManager.incEvent(eventName)

        Assert.assertTrue(equals(statsManager.getEventStatisticsByName(anotherEventName), 0.0))
        Assert.assertEquals(1, statsManager.getAllEventStatistics().size.toLong())
    }

    @Test
    fun testClockEventsStatisticsFixedTimeOneEventOneIncrement() {
        val (statsManager, eventName) = prepare()
        statsManager.incEvent(eventName)

        Assert.assertTrue(equals(statsManager.getEventStatisticsByName(eventName), 1.0 / MINUTES_IN_HOUR.toDouble()))
        Assert.assertEquals(1, statsManager.getAllEventStatistics().size.toLong())
    }

    @Test
    fun testClockEventsStatisticsFixedTimeOneEventManyIncrements() {
        val (statsManager, eventName) = prepare()
        for (i in 1..500) {
            statsManager.incEvent(eventName)

            Assert.assertTrue(equals(statsManager.getEventStatisticsByName(eventName), i / 60.0))
            Assert.assertEquals(1, statsManager.getAllEventStatistics().size.toLong())
        }
    }

    @Test
    fun testClockEventsStatisticsChangingTimeNoEvents() {
        val clock = SettableClock(Instant.ofEpochSecond(0L))
        val statsManager = ClockEventsStatistics(clock)
        val eventName = "incident"

        Assert.assertTrue(equals(statsManager.getEventStatisticsByName(eventName), 0.0))
        Assert.assertEquals(0, statsManager.getAllEventStatistics().size.toLong())

        clock.now = Instant.ofEpochSecond(123)
        Assert.assertTrue(equals(statsManager.getEventStatisticsByName(eventName), 0.0))
        Assert.assertEquals(0, statsManager.getAllEventStatistics().size.toLong())

        clock.now = Instant.ofEpochSecond(3800)
        Assert.assertTrue(equals(statsManager.getEventStatisticsByName(eventName), 0.0))
        Assert.assertEquals(0, statsManager.getAllEventStatistics().size.toLong())
    }

    @Test
    fun testClockEventsStatisticsChangingTimeOneEventStatsForNonExisting() {
        val clock = SettableClock(Instant.ofEpochSecond(0L))
        val statsManager = ClockEventsStatistics(clock)
        val eventName = "incident"
        val anotherEventName = "another incident"
        statsManager.incEvent(eventName)

        Assert.assertTrue(equals(statsManager.getEventStatisticsByName(anotherEventName), 0.0))
        Assert.assertEquals(1, statsManager.getAllEventStatistics().size.toLong())

        clock.now = Instant.ofEpochSecond(123)
        Assert.assertTrue(equals(statsManager.getEventStatisticsByName(anotherEventName), 0.0))
        Assert.assertEquals(1, statsManager.getAllEventStatistics().size.toLong())

        clock.now = Instant.ofEpochSecond(3800)
        Assert.assertTrue(equals(statsManager.getEventStatisticsByName(anotherEventName), 0.0))
        Assert.assertEquals(1, statsManager.getAllEventStatistics().size.toLong())
    }

    @Test
    fun testClockEventsStatisticsChangingTimeOneEventOneIncrement() {
        val clock = SettableClock(Instant.ofEpochSecond(0L))
        val statsManager = ClockEventsStatistics(clock)
        val eventName = "incident"
        statsManager.incEvent(eventName)

        Assert.assertTrue(equals(statsManager.getEventStatisticsByName(eventName), 1.0 / MINUTES_IN_HOUR.toDouble()))
        Assert.assertEquals(1, statsManager.getAllEventStatistics().size.toLong())

        clock.now = Instant.ofEpochSecond(123)
        Assert.assertTrue(equals(statsManager.getEventStatisticsByName(eventName), 1.0 / MINUTES_IN_HOUR.toDouble()))
        Assert.assertEquals(1, statsManager.getAllEventStatistics().size.toLong())
    }

    @Test
    fun testClockEventsStatisticsChangingTimeOneEventOneIncrementHourPassed() {
        val clock = SettableClock(Instant.ofEpochSecond(0L))
        val statsManager = ClockEventsStatistics(clock)
        val eventName = "incident"
        statsManager.incEvent(eventName)

        Assert.assertTrue(equals(statsManager.getEventStatisticsByName(eventName), 1.0 / MINUTES_IN_HOUR.toDouble()))
        Assert.assertEquals(1, statsManager.getAllEventStatistics().size.toLong())

        clock.now = Instant.ofEpochSecond(123)
        Assert.assertTrue(equals(statsManager.getEventStatisticsByName(eventName), 1.0 / MINUTES_IN_HOUR.toDouble()))
        Assert.assertEquals(1, statsManager.getAllEventStatistics().size.toLong())

        clock.now = Instant.ofEpochSecond(3600)
        Assert.assertTrue(equals(statsManager.getEventStatisticsByName(eventName), 1.0 / MINUTES_IN_HOUR.toDouble()))
        Assert.assertEquals(1, statsManager.getAllEventStatistics().size.toLong())

        clock.now = Instant.ofEpochSecond(3601)
        Assert.assertTrue(equals(statsManager.getEventStatisticsByName(eventName), 0.0))
        Assert.assertEquals(1, statsManager.getAllEventStatistics().size.toLong())
    }

    @Test
    fun testClockEventsStatisticsChangingTimeOneEventFewIncrementsWithinAnHour() {
        val clock = SettableClock(Instant.ofEpochSecond(0L))
        val statsManager = ClockEventsStatistics(clock)
        val eventName = "incident"
        statsManager.incEvent(eventName)

        Assert.assertTrue(equals(statsManager.getEventStatisticsByName(eventName), 1.0 / MINUTES_IN_HOUR.toDouble()))
        Assert.assertEquals(1, statsManager.getAllEventStatistics().size.toLong())

        clock.now = Instant.ofEpochSecond(123)
        Assert.assertTrue(equals(statsManager.getEventStatisticsByName(eventName), 1.0 / MINUTES_IN_HOUR.toDouble()))
        Assert.assertEquals(1, statsManager.getAllEventStatistics().size.toLong())

        clock.now = Instant.ofEpochSecond(3600)
        Assert.assertTrue(equals(statsManager.getEventStatisticsByName(eventName), 1.0 / MINUTES_IN_HOUR.toDouble()))
        Assert.assertEquals(1, statsManager.getAllEventStatistics().size.toLong())

        statsManager.incEvent(eventName)
        clock.now = Instant.ofEpochSecond(3600)
        Assert.assertTrue(equals(statsManager.getEventStatisticsByName(eventName), 2.0 / MINUTES_IN_HOUR.toDouble()))
        Assert.assertEquals(1, statsManager.getAllEventStatistics().size.toLong())

        clock.now = Instant.ofEpochSecond(3601)
        Assert.assertTrue(equals(statsManager.getEventStatisticsByName(eventName), 1.0 / MINUTES_IN_HOUR.toDouble()))
        Assert.assertEquals(1, statsManager.getAllEventStatistics().size.toLong())
    }

    @Test
    fun testClockEventsStatisticsChangingTimeManyEventsManyIncrements() {
        val clock = SettableClock(Instant.ofEpochSecond(0L))
        val statsManager = ClockEventsStatistics(clock)
        val events = arrayOf("incident0", "incident1", "incident2", "incident3", "incident4", "incident5", "incident6", "incident7")
        val periods = intArrayOf(10, 11, 24, 31, 47, 53, 66, 79)
        val count = 500
        for (i in 1..count) {
            for (j in events.indices) {
                val eventName = events[j]
                val period = periods[j]
                clock.now = Instant.ofEpochSecond(i * period.toLong())
                statsManager.incEvent(eventName)
            }
        }

        Assert.assertEquals(events.size, statsManager.getAllEventStatistics().size)

        clock.now = Instant.ofEpochSecond(0L)
        for (eventName in events) {
            Assert.assertTrue(equals(statsManager.getEventStatisticsByName(eventName), 0.0))
        }

        val max = count * periods[periods.size - 1]
        for (i in 0 until max) {
            clock.now = Instant.ofEpochSecond(i.toLong())
            for (j in events.indices) {
                val eventName = events[j]
                val period = periods[j]
                if (i < count * period) {
                    var first = period * ((i - SECONDS_IN_HOUR) / period)
                    if ((i - SECONDS_IN_HOUR) % period != 0) {
                        first += period
                    }
                    val start = period.coerceAtLeast(first)
                    val cnt = 0.coerceAtLeast(i - start + period) / period

                    Assert.assertTrue(equals(statsManager.getEventStatisticsByName(eventName), cnt / MINUTES_IN_HOUR.toDouble()))
                }
            }
        }
    }

    @Test
    fun testClockEventsStatisticsChangingTimeManyEventsManyIncrementsTimeGoesBackwards() {
        val clock = SettableClock(Instant.ofEpochSecond(0L))
        val statsManager = ClockEventsStatistics(clock)
        val events = arrayOf("incident0", "incident1", "incident2", "incident3", "incident4", "incident5", "incident6", "incident7")
        val periods = intArrayOf(10, 11, 24, 31, 47, 53, 66, 79)
        val count = 500
        for (i in count downTo 1) {
            for (j in events.indices) {
                val eventName = events[j]
                val period = periods[j]
                clock.now = Instant.ofEpochSecond(i * period.toLong())
                statsManager.incEvent(eventName)
            }
        }
        Assert.assertEquals(events.size, statsManager.getAllEventStatistics().size)
        clock.now = Instant.ofEpochSecond(0L)
        for (eventName in events) {
            Assert.assertTrue(equals(statsManager.getEventStatisticsByName(eventName), 0.0))
        }
        val max = count * periods[periods.size - 1]
        for (i in 0 until max) {
            clock.now = Instant.ofEpochSecond(i.toLong())
            for (j in events.indices) {
                val eventName = events[j]
                val period = periods[j]
                if (i < count * period) {
                    var first = period * ((i - SECONDS_IN_HOUR) / period)
                    if ((i - SECONDS_IN_HOUR) % period != 0) {
                        first += period
                    }
                    val start = period.coerceAtLeast(first)
                    val cnt = 0.coerceAtLeast(i - start + period) / period
                    Assert.assertTrue(equals(statsManager.getEventStatisticsByName(eventName), cnt / MINUTES_IN_HOUR.toDouble()))
                }
            }
        }
    }

    companion object {
        private const val EPSILON = 1e-20
        private const val MINUTES_IN_HOUR: Int = 60
        private const val SECONDS_IN_HOUR: Int = 3600
    }
}