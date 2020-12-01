package com.kochetkov.calculator.visitors

import com.kochetkov.calculator.tokinazer.Bracket
import com.kochetkov.calculator.tokinazer.NumberToken
import com.kochetkov.calculator.tokinazer.Operation

interface TokenVisitor {
    fun visit(token: NumberToken)
    fun visit(token: Bracket)
    fun visit(token: Operation)
}
