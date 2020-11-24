package com.kochetkov.spring.mvc.model

data class Item(val id: Long, val description: String, val listId: Long, var isDone: Boolean = false)