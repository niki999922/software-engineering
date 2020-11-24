package com.kochetkov.spring.mvc.dao

import org.springframework.stereotype.Component
import com.kochetkov.spring.mvc.model.Item
import com.kochetkov.spring.mvc.model.ItemDto
import com.kochetkov.spring.mvc.model.TODODto
import com.kochetkov.spring.mvc.model.TODOList
import java.util.concurrent.atomic.AtomicLong

@Component
class ItemInMemoryDao : ItemDao {
    private val lastId = AtomicLong(0)
    private val listId = AtomicLong(0)
    private val lists = mutableListOf(TODOList(0, "All"))

    override fun addItem(item: ItemDto) = lastId.incrementAndGet().also { id ->
        lists.find { it.id == item.listId }!!.add(Item(id, item.description, item.listId))
    }

    override fun getLists(): List<TODOList> {
        return lists
    }

    override fun done(listId: Long, id: Long) {
        lists.find { it.id == listId }!!.items.find { it.id == id }!!.isDone = true
    }

    override fun deleteItem(listId: Long, id: Long) {
        lists.find { it.id == listId }!!.items.removeIf { it.id == id }
    }

    override fun addList(list: TODODto) = listId.incrementAndGet().also { id ->
        lists.add(TODOList(id, list.description))
    }

    override fun deleteList(listId: Long) {
        lists.removeIf { it.id == listId }
    }
}