package com.kochetkov.calculator.tokinazer

import java.lang.IllegalArgumentException

object Tokenizer {
    private val ARITHMETIC_STATE = ArithmeticState()
    private val EOF_STATE = EofState()
    private var currentState: State = ARITHMETIC_STATE
    private var tokens = mutableListOf<Token>()

    fun tokenize(expression: String): List<Token> {
        tokens.clear()
        currentState = ARITHMETIC_STATE
        expression.forEach {
            currentState.processChar(it)
        }
        currentState.processEof()
        return tokens
    }

    private abstract class State {
        abstract fun processChar(char: Char)
        open fun processEof() {
            currentState = EOF_STATE
        }
    }

    private class ArithmeticState: State() {
        override fun processChar(char: Char) {
            when(char) {
                '+' -> tokens.add(Plus)
                '-' -> tokens.add(Minus)
                '*' -> tokens.add(Mul)
                '/' -> tokens.add(Div)
                '(' -> tokens.add(LeftBracket)
                ')' -> tokens.add(RightBracket)
                in '0'..'9' -> {
                    currentState = NumberState()
                    currentState.processChar(char)
                }
                else -> {
                    if (!char.isWhitespace()) {
                        throw IllegalArgumentException("Unknown char: $char.")
                    }
                }
            }
        }
    }

    private class NumberState: State() {
        private var number = 0
        override fun processChar(char: Char) {
            when(char) {
                in '0'..'9' -> {
                    number = number * 10 + char.toString().toInt()
                }
                else -> {
                    tokens.add(NumberToken(number))
                    currentState = ARITHMETIC_STATE
                    currentState.processChar(char)
                }
            }
        }

        override fun processEof() {
            tokens.add(NumberToken(number))
        }
    }

    private class EofState: State() {
        override fun processChar(char: Char) {}
    }
}