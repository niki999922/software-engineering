package com.kochetkov.aop.domain

import com.kochetkov.aop.domain.Stats.Companion.attack
import com.kochetkov.aop.domain.Stats.Companion.fireball
import com.kochetkov.aop.domain.Stats.Companion.health

interface PersonManager {
    fun addPerson(person: Person): Boolean
    fun getPerson(): Person
    fun attack(damage: Int): Int
    fun health(health: Int): Int
    fun fireball(stamina: Int): Int
    fun printStats(): String
}

class PersonManagerImpl : PersonManager {
    private var _person = Person("", Stats(100_000, 3_000, 4_000))

    override fun addPerson(person: Person): Boolean {
        _person = person
        return true
    }

    override fun getPerson(): Person = _person

    override fun attack(damage: Int): Int {
        _person.stats.attack { health ->
            if (health > 100) damage else (if (health > 0) 1 else 0)
        }
        return _person.stats.health
    }

    override fun health(health: Int): Int {
        if (_person.stats.magic <= 0) return _person.stats.magic
        _person.stats.health(health) { it }
        return _person.stats.magic
    }

    override fun fireball(stamina: Int): Int {
        return _person.stats.stamina.also {
            if (_person.stats.stamina < stamina) return@also
            _person.stats.fireball { it }
            _person.stats.stamina
        }
    }

    override fun printStats() =
        "\"${_person.name}\" have h${_person.stats.health} m${_person.stats.magic} s${_person.stats.stamina}"
}


