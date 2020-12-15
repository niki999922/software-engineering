package com.kochetkov.aop.aspect

class Tree(val name: String) {
    val children: MutableList<Tree> = mutableListOf()
    fun print(i: Int) {
        val l = "-".repeat(i) + name
        println(l)
        for (c in children) {
            c.print(l.length)
        }
    }
}