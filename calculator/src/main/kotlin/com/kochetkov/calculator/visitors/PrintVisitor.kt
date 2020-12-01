package com.kochetkov.calculator.visitors

import com.kochetkov.calculator.tokinazer.Bracket
import com.kochetkov.calculator.tokinazer.NumberToken
import com.kochetkov.calculator.tokinazer.Operation
import com.kochetkov.calculator.tokinazer.Token

object PrintVisitor: TokenVisitor {
    override fun visit(token: NumberToken) = println(token)

    override fun visit(token: Bracket) = println(token)

    override fun visit(token: Operation) = println(token)

    fun visit(tokens: List<Token>) {
        println(tokens.joinToString(" ") { it.toString() })
    }
}