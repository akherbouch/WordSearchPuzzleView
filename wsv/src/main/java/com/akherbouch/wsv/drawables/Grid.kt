package com.akherbouch.wsv.drawables

import android.graphics.Canvas
import android.graphics.Paint
import com.akherbouch.wsv.utils.DrawUtil

class Grid(
    private val cols: Int,
    private val rows: Int,
    private val length: Int,
    private val padX: Int,
    private val padY: Int
) : Drawable() {

    private val gridPaint = DrawUtil.createFillPaint(-0xe8c3b2)

    override fun draw(canvas: Canvas) {
        for (i in 1 until cols) {
            canvas.drawRect(
                (length * i - 1 + padX).toFloat(), (padY + 1).toFloat(),
                (length * i + 1 + padX).toFloat(), (length * rows + padY - 1).toFloat(),
                gridPaint
            )
        }
        for (i in 1 until rows) {
            canvas.drawRect(
                (padX + 1).toFloat(), (length * i - 1 + padY).toFloat(),
                (length * cols + padX - 1).toFloat(), (length * i + 1 + padY).toFloat(),
                gridPaint
            )
        }
    }
}
