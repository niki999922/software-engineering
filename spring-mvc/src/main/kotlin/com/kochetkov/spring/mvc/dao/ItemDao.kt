package com.kochetkov.spring.mvc.dao

import com.kochetkov.spring.mvc.model.ItemDto
import com.kochetkov.spring.mvc.model.TODODto
import com.kochetkov.spring.mvc.model.TODOList

interface ItemDao {
  fun addItem(item: ItemDto): Long
  fun getLists(): List<TODOList>
  fun done(listId: Long, id: Long)
  fun deleteItem(listId: Long, id: Long)
  fun addList(list: TODODto): Long
  fun deleteList(listId: Long)
}