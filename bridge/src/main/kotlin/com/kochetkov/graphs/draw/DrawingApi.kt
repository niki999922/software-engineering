package com.kochetkov.graphs.draw

import com.kochetkov.graphs.figure.Circle
import com.kochetkov.graphs.figure.Point

interface DrawingApi {
    fun getDrawingAreaWidth(): Int
    fun getDrawingAreaHeight(): Int
    fun drawCircle(circle: Circle)
    fun drawLine(from: Point, to: Point)
    fun show()
}