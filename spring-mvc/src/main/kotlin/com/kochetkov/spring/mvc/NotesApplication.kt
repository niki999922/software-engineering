package com.kochetkov.spring.mvc

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class NotesApplication

fun main(args: Array<String>) {
  runApplication<NotesApplication>(*args)
}
