package com.kochetkov.graphs

import com.kochetkov.graphs.draw.AwtDrawing
import com.kochetkov.graphs.draw.FxDrawing
import com.kochetkov.graphs.graph.ListGraph
import com.kochetkov.graphs.graph.MatrixGraph
import java.lang.IllegalArgumentException

fun main(args: Array<String>) {
  when (args[0]) {
    "awt" -> AwtDrawing()
    "fx" -> FxDrawing()
    else -> throw IllegalArgumentException("Bad framework of painting, use \"awt\" or \"fx\"")
  }.let { api ->
    when (args[1]) {
      "list" -> ListGraph(api, "src/main/resources/list.txt")
      "matrix" -> MatrixGraph(api, "src/main/resources/matrix.txt")
      else -> throw IllegalArgumentException("Invalid format of file")
    }
  }.drawGraph()
}



