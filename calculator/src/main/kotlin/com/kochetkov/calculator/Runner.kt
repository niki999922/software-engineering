package com.kochetkov.calculator

import com.kochetkov.calculator.tokinazer.Tokenizer
import com.kochetkov.calculator.visitors.CalcVisitor
import com.kochetkov.calculator.visitors.ParserVisitor
import com.kochetkov.calculator.visitors.PrintVisitor

class Runner(private val expression: String) {
    private val tokens = Tokenizer.tokenize(expression)
    private val rpnTokens = lazy { ParserVisitor().visit(tokens) }

    fun printTokens() {
        PrintVisitor.visit(tokens)
    }

    fun printRpn() {
        PrintVisitor.visit(rpnTokens.value)
    }

    fun getCalcValue(): Int {
        val calcVisitor = CalcVisitor()
        return calcVisitor.visit(rpnTokens.value)
    }

    fun printAllInfo() {
        printTokens()
        printRpn()
        println("$expression = ${getCalcValue()}")
    }
}