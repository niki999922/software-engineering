package com.kochetkov.calculator.visitors

import com.kochetkov.calculator.tokinazer.*
import java.lang.IllegalArgumentException

class ParserVisitor: TokenVisitor {
    private val parserTokens = mutableListOf<Token>()
    private val stack = mutableListOf<Token>()

    override fun visit(token: NumberToken) {
        parserTokens.add(token)
    }

    override fun visit(token: Bracket) {
        when(token) {
            is LeftBracket -> stack.add(token)
            is RightBracket -> {
                while (stack.isNotEmpty() && stack.last() !is LeftBracket) {
                    parserTokens.add(stack.removeLast())
                }
                if (stack.isEmpty()) {
                    throw IllegalArgumentException("Incorrect expression. Not found left bracket.")
                } else {
                    stack.removeLast()
                }
            }
        }
    }

    override fun visit(token: Operation) {
        when(token) {
            is Plus, is Minus -> {
                if (stack.isNotEmpty() && stack.last() !is LeftBracket) {
                    parserTokens.add(stack.removeLast())
                }
                stack.add(token)
            }
            is Mul, is Div -> {
                if (stack.isNotEmpty() && (stack.last() is Mul || stack.last() is Div)) {
                    parserTokens.add(stack.removeLast())
                }
                stack.add(token)
            }
        }
    }

    fun visit(tokens: List<Token>): List<Token> {
        tokens.forEach{ token ->
            token.accept(this)
        }
        while (stack.isNotEmpty()) {
            parserTokens.add(stack.removeLast())
        }
        return parserTokens
    }
}