package com.kochetkov.aop.domain

class Stats(
    var health: Int,
    var magic: Int,
    var stamina: Int
) {
    companion object {
        fun Stats.attack(block: (health: Int) -> Int) {
            health -= block(health)
        }

        fun Stats.health(cost: Int, block: (magic: Int) -> Int) {
            health += block(health)
            magic -= block(cost)
        }

        fun Stats.fireball(block: (stamina: Int) -> Int) {
            stamina -= block(stamina)
        }
    }
}