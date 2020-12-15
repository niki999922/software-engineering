package com.kochetkov.aop.aspect

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import java.util.*

@Aspect
class LoggingExecutionTimeAspect {
    private val executions: MutableMap<String, MutableList<Long>> = HashMap()

    @Around("execution(* com.kochetkov.aop.domain..*.*(..))")
    fun logExecutionTime(joinPoint: ProceedingJoinPoint): Any {
        val startNs = System.nanoTime()
        val methodName = String.format("%s.%s", joinPoint.signature.declaringTypeName, joinPoint.signature.name)
        val result = joinPoint.proceed(joinPoint.args)
        if (!executions.containsKey(methodName)) {
            executions[methodName] = ArrayList()
        }
        executions[methodName]!!.add(System.nanoTime() - startNs)
        return result
    }

    fun printResults() {
        val tree = Tree("")
        executions.forEach { (name, list) ->
            val parts = name.split(".").toMutableList()
            val totalCalls = list.size
            val totalTime = list.sum()
            val avgTimeOnAction = totalTime / totalCalls
            var curTree = tree
            var index = 0
            parts.forEachIndexed { ind, part ->
                var found = false
                for (t in curTree.children) {
                    if (t.name == part) {
                        curTree = t
                        found = true
                        break
                    }
                }
                index++
                if (!found) {
                    if (index == parts.size) {
                        parts[ind] += " actions: $totalCalls | avg: $avgTimeOnAction ns | total: $totalCalls ns"
                    }
                    val newTree = Tree(parts[ind])
                    curTree.children.add(newTree)
                    curTree = newTree
                }
            }
        }
        tree.print(0)
    }
}