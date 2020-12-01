package com.kochetkov.calculator

fun main() {
    Runner("2 * 4 + 2 * 3").printAllInfo()
    println("\n=====================\n")
    Runner("2 * 8 - 3 * 4 / 6").printAllInfo()
    println("\n=====================\n")
    Runner("(10 - 7) * (12 - 9)").printAllInfo()
}