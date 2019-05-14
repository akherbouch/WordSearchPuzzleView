package com.akherbouch.wsv.drawables

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint

abstract class Drawable {

    abstract fun draw(canvas: Canvas)

    class Builder(private val context: Context, private val length: Int, private val padX: Int, private val padY: Int) {

        fun buildBackground(width: Int, height: Int): Background
            = Background(context.resources, width, height, padX, padY)

        fun buildGrid(cols: Int, rows: Int): Grid
            = Grid(cols, rows, length, padX, padY)

        fun buildLetter(c: Int, r: Int, ch: String, textPaint: Paint): Letter
            = Letter((padX + c * length).toFloat(), (padY + r * length).toFloat(), length, ch, textPaint)

        fun buildLine(startCol: Int, startRow: Int, color: Int): Line
            = Line(startCol, startRow, length, color, padX, padY)

        fun buildLine(lineStr: String): Line
            = Line(lineStr, length, padX, padY)

    }
}
