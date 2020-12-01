package com.kochetkov.stats.clock

import java.time.Instant

interface Clock {
    fun now(): Instant
}