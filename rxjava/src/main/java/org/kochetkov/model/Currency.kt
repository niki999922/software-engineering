package org.kochetkov.model

enum class Currency(private val cost: Double) {
    RUB(1.0),
    EUR(0.011),
    USD(0.014);

    companion object {
        fun Currency.convert(to: Currency, value: Double) = value * to.cost / cost
    }
}