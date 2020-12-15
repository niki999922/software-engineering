package com.kochetkov.aop.aspect

class Tree(val name: String) {
    val children: MutableList<Tree> = mutableListOf()

    fun print(i: Int) {
        val l = "|" + "_".repeat(i) + name

        println(l)
        children.forEach { it.print(l.length) }
    }
}