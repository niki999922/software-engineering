package com.kochetkov.calculator.tokinazer

import com.kochetkov.calculator.visitors.TokenVisitor

sealed class Token {
    abstract fun accept(visitor: TokenVisitor)
}

data class NumberToken(val n: Int): Token() {
    override fun accept(visitor: TokenVisitor) {
        visitor.visit(this)
    }

    override fun toString() = "NUMBER($n)"
}

sealed class Bracket: Token() {
    override fun accept(visitor: TokenVisitor) {
        visitor.visit(this)
    }
}

object LeftBracket: Bracket() {
    override fun toString() = "LEFT"
}

object RightBracket: Bracket() {
    override fun toString() = "RIGHT"
}

sealed class Operation: Token() {
    override fun accept(visitor: TokenVisitor) {
        visitor.visit(this)
    }
}

object Plus: Operation() {
    override fun toString() = "PLUS"
}

object Minus: Operation() {
    override fun toString() = "MINUS"
}

object Mul: Operation() {
    override fun toString() = "MUL"
}

object Div: Operation() {
    override fun toString() = "DIV"
}
