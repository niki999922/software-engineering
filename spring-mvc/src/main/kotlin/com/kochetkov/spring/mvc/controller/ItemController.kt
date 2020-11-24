package com.kochetkov.spring.mvc.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.*
import com.kochetkov.spring.mvc.dao.ItemDao
import com.kochetkov.spring.mvc.model.ItemDto
import com.kochetkov.spring.mvc.model.TODODto
import com.kochetkov.spring.mvc.model.TODOList

@Controller
class ItemController(private val itemDao: ItemDao) {
  @PostMapping("/add-item")
  fun addItem(@ModelAttribute("itemDto") itemDto: ItemDto): String {
    itemDao.addItem(itemDto)
    return "redirect:/get-items"
  }

  @PostMapping("/add-list")
  fun addList(@ModelAttribute("listDto") itemDto: TODODto): String {
    itemDao.addList(itemDto)
    return "redirect:/get-items"
  }

  @GetMapping("/done/{listId}/{id}")
  fun markDone(@PathVariable("listId") listId: Long, @PathVariable("id") id: Long): String {
    itemDao.done(listId, id)
    return "redirect:/get-items"
  }

  @GetMapping("/delete/{listId}/{id}")
  fun deleteTask(@PathVariable("listId") listId: Long, @PathVariable("id") id: Long): String {
    itemDao.deleteItem(listId, id)
    return "redirect:/get-items"
  }

  @GetMapping("/deleteList/{listId}")
  fun deleteList(@PathVariable("listId") listId: Long): String {
    itemDao.deleteList(listId)
    return "redirect:/get-items"
  }

  @GetMapping("/get-items")
  fun getItems(map: ModelMap): String {
    prepareModelMap(map, itemDao.getLists())
    return "index"
  }

  @GetMapping("/")
  fun getIndex(map: ModelMap): String {
    prepareModelMap(map, itemDao.getLists())
    return "index"
  }

  private fun prepareModelMap(map: ModelMap, lists: List<TODOList>) {
    map.addAttribute("itemDto", ItemDto())
    map.addAttribute("lists", lists)
    map.addAttribute("listDto", TODODto())
  }
}
