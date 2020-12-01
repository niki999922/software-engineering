package com.kochetkov.calculator.visitors

import com.kochetkov.calculator.tokinazer.*
import java.lang.IllegalArgumentException

class CalcVisitor: TokenVisitor {
    private val calcStack = mutableListOf<Int>()

    override fun visit(token: NumberToken) {
        calcStack.add(token.n)
    }

    override fun visit(token: Bracket) {
        throw IllegalArgumentException("Incorrect Reverse Polish notation")
    }

    override fun visit(token: Operation) {
        if (calcStack.size < 2)
            throw IllegalArgumentException("Incorrect Reverse Polish notation")
        val y = calcStack.removeLast()
        val x = calcStack.removeLast()
        when(token) {
            is Plus -> {
                calcStack.add(x + y)
            }
            is Minus -> {
                calcStack.add(x - y)
            }
            is Mul -> {
                calcStack.add(x * y)
            }
            is Div -> {
                calcStack.add(x / y)
            }
        }
    }

    fun visit(tokens: List<Token>): Int {
        tokens.forEach {
            it.accept(this)
        }
        if (calcStack.size != 1) {
            throw IllegalArgumentException("Incorrect Reverse Polish notation")
        }
        return calcStack.last()
    }
}