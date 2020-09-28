package ru.ifmo.kochetkov

import java.util.*
import kotlin.collections.HashMap

class LRUCacheImpl<K, V>(private val maxSize: Int = 10) : LRUCache<K, V> {
    private val deque = ArrayDeque<K>(maxSize)
    private val map = HashMap<K, V>()

    init {
        require(maxSize > 0) { "Capacity must be positive" }
    }

    override fun refresh(key: K): LRUCache<K, V> {
        if (deque.contains(key)) {
            deque.remove(key)
            deque.add(key)
            assert(deque.contains(key))
        }
        return this
    }

    override fun insert(key: K, value: V): LRUCache<K, V> {
        if (deque.contains(key)) {
            refresh(key)
        } else {
            if (deque.size == maxSize) {
                map.remove(deque.removeFirst())
                assert(deque.size + 1 == maxSize)
            }
            deque.add(key)
            assert(deque.contains(key))

        }
        map[key] = value
        assert(map.contains(key))

        return this
    }

    override fun get(key: K) = map[key]

    internal fun dequeSize() = deque.size
    internal fun mapSize() = map.size
}