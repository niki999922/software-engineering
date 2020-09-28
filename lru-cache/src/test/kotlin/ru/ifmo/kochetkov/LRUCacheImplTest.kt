package ru.ifmo.kochetkov

import org.junit.Assert
import org.junit.Test


class LRUCacheImplTest {

    @Test
    fun `expected map size less then max`() {
        val cache = LRUCacheImpl<Int, Int>(4)
        cache
            .insert(1, 1)
            .insert(2, 2)
            .insert(3, 3)
            .insert(4, 4)
            .insert(5, 5)
            .insert(6, 6)
            .insert(7, 7)
            .insert(8, 8)

        Assert.assertEquals(4, cache.mapSize())
    }


    @Test
    fun `expected empty deque size`() {
        val cache = LRUCacheImpl<Int, Int>(100)

        Assert.assertEquals(0, cache.dequeSize())
    }

    @Test
    fun `expected middle deque size`() {
        val cache = LRUCacheImpl<Int, Int>(10)
        cache
            .insert(1, 1)
            .insert(2, 2)
            .insert(3, 3)
            .refresh(3)
            .refresh(3)
            .refresh(3)
            .insert(4, 4)
            .insert(5, 5)
            .refresh(1123)
            .refresh(1333)
            .refresh(10)
            .refresh(1321)

        Assert.assertEquals(5, cache.dequeSize())
    }


    @Test
    fun `expected max deque size`() {
        val cache = LRUCacheImpl<Int, Int>(3)
        cache
            .insert(1, 1)
            .insert(2, 2)
            .insert(3, 3)
            .insert(4, 4)

        Assert.assertEquals(3, cache.dequeSize())
    }

    @Test
    fun `test refresh`() {
        val cache = LRUCacheImpl<Int, Int>(3)
        cache
            .insert(1, 1)
            .insert(2, 2)
            .insert(3, 3)
            .refresh(1)
            .insert(4, 4)

        Assert.assertEquals(1, cache.get(1))
    }

    @Test
    fun `stable work on overflow`() {
        val cache = LRUCacheImpl<Int, Int>(4)
        for (i in 0 until 30) {
            cache.insert(i, i)
        }
    }

    @Test
    fun `get one element when overflow`() {
        val cache = LRUCacheImpl<Int, Int>(4)
        cache
            .insert(1, 1)
            .insert(2, 2)
            .insert(3, 3)
            .insert(4, 4)
            .insert(5, 5)

        Assert.assertNull(cache.get(1))
        Assert.assertEquals(2, cache.get(2))
    }


    @Test
    fun `throw IllegalArgumentException when negative size`() {
        Assert.assertThrows(IllegalArgumentException::class.java) {
            LRUCacheImpl<Int, Int>(-4)
        }
    }
}