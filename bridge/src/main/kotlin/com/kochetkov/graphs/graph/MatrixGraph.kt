package com.kochetkov.graphs.graph

import com.kochetkov.graphs.draw.DrawingApi
import com.kochetkov.graphs.figure.Edge
import java.io.File
import java.lang.IllegalArgumentException

typealias Matrix = List<List<Boolean>>

class MatrixGraph(
    drawingApi: DrawingApi,
    fileName: String
) : Graph(drawingApi) {
    private val matrix: Matrix

    init {
        val lines = File(fileName).readLines()
        matrix = lines.map { line ->
            line.map { char ->
                when (char) {
                    '0' -> false
                    '1' -> true
                    else -> throw IllegalArgumentException("Incorrect symbol: \"$char\"")
                }
            }
        }
    }

    override fun drawGraph() {
        super.drawListGraph(matrix.size, matrixToList())
    }

    private fun matrixToList(): List<Edge> {
        val edges = mutableListOf<Edge>()
        for (i: Int in 0..matrix.lastIndex) {
            for (j: Int in 0..i) {
                if (matrix[i][j]) {
                    edges.add(Edge(i, j))
                }
            }
        }
        return edges
    }
}