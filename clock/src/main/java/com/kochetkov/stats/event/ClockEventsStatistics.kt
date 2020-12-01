package com.kochetkov.stats.event

import com.kochetkov.stats.clock.Clock
import java.util.*

class ClockEventsStatistics(private val clock: Clock) : EventsStatistics {
    private val events: MutableMap<String, MutableList<Long>> = HashMap()

    override fun incEvent(name: String) {
        val now = clock.now()
        if (!events.containsKey(name)) {
            events[name] = ArrayList()
        }
        events[name]!!.add(now.epochSecond)
    }

    override fun getEventStatisticsByName(name: String): Double {
        if (!events.containsKey(name)) {
            return 0.0
        }
        val eventsByName: List<Long> = events[name]!!
        val now = clock.now().epochSecond
        val hourAgo = (now - SECONDS_IN_HOUR).coerceAtLeast(0L)
        val eventsByNameLastHourCount = eventsByName.filter { it in hourAgo..now }.count()
        return eventsByNameLastHourCount / MINUTES_IN_HOUR.toDouble()
    }

    override fun getAllEventStatistics() = events.map { (key, _) -> key to getEventStatisticsByName(key) }.toMap()

    override fun printStatistics() {
        events.forEach { (key, value) ->
            val validEventsCount = value.stream().filter { t: Long -> t >= 0 }.count()
            System.out.printf("Event: %s, rpm: %f%n", key, validEventsCount / MINUTES_IN_HOUR.toDouble())
        }
    }

    companion object {
        private const val MINUTES_IN_HOUR: Long = 60
        private const val SECONDS_IN_HOUR: Long = 3600
    }
}