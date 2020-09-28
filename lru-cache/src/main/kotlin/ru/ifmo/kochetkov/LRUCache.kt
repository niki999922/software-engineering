package ru.ifmo.kochetkov

interface LRUCache<K, V> {
    fun refresh(key: K): LRUCache<K, V>
    fun insert(key: K, value: V): LRUCache<K, V>
    fun get(key: K): V?
}