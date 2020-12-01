package com.kochetkov.stats.event

interface EventsStatistics {
    fun incEvent(name: String)
    fun getEventStatisticsByName(name: String): Double
    fun getAllEventStatistics(): Map<String, Double>
    fun printStatistics()
}