package com.kochetkov.graphs.draw

import com.kochetkov.graphs.figure.Circle
import com.kochetkov.graphs.figure.Line
import com.kochetkov.graphs.figure.Point
import javafx.application.Application
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color
import javafx.stage.Stage


class FxDrawing : DrawingApi, Application() {
    private val width = 1920
    private val height = 1080

    override fun start(primaryStage: Stage) {
        primaryStage.title = "Graph"
        val root = Group()
        val canvas = Canvas(width.toDouble(), height.toDouble())
        val gc: GraphicsContext = canvas.graphicsContext2D
        LineWrapper.lines.forEach { (from, to) ->
            gc.fill = Color.DARKGREY
            gc.moveTo(from.x, from.y)
            gc.lineTo(to.x, to.y)
            gc.stroke()
        }
        CircleWrapper.circles.forEach { circle ->
            gc.fill = Color.ORANGE
            gc.fillOval(
              circle.center.x - circle.radius,
              circle.center.y - circle.radius,
              circle.radius * 2,
              circle.radius * 2
            )
        }
        root.children.add(canvas)
        primaryStage.scene = Scene(root)
        primaryStage.show()
    }

    override fun show() {
        launch(this::class.java)
    }

    override fun drawCircle(circle: Circle) {
        CircleWrapper.addCircle(circle)
    }

    override fun drawLine(from: Point, to: Point) {
        LineWrapper.addLine(from, to)
    }

    override fun getDrawingAreaWidth() = width
    override fun getDrawingAreaHeight() = height

    object CircleWrapper {
        val circles = mutableListOf<Circle>()

        fun addCircle(circle: Circle) {
            circles.add(circle)
        }
    }

    object LineWrapper {
        val lines = mutableListOf<Line>()

        fun addLine(from: Point, to: Point) {
            lines.add(Line(from, to))
        }
    }
}