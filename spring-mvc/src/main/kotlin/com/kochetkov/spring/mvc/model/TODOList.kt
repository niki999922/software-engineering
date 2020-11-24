package com.kochetkov.spring.mvc.model

data class TODOList(val id: Long, val description: String) {
  val items = mutableListOf<Item>()
  fun add(item: Item) = items.add(item)
}