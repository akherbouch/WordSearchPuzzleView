package com.akherbouch.wsv.drawables

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect

/**
 * Created by AYYYOUB on 13/06/2017.
 */

class Letter(
    private val x: Float,
    private val y: Float,
    length: Int,
    private val letter: String,
    private val textPaint: Paint
) : Drawable() {

    private val xc: Float
    private val yc: Float
    var isDiscovered: Boolean = false

    init {
        val r = Rect(this.x.toInt(), this.y.toInt(), this.x.toInt() + length, this.y.toInt() + length)
        val cHeight = r.height()
        val cWidth = r.width()
        textPaint.textAlign = Paint.Align.LEFT
        textPaint.getTextBounds(letter, 0, letter.length, r)
        xc = cWidth / 2f - r.width() / 2f - r.left.toFloat()
        yc = cHeight / 2f + r.height() / 2f - r.bottom
    }

    override fun draw(canvas: Canvas) {
        if (isDiscovered) {
            val textColor = textPaint.color
            textPaint.color = -0xb13662
            canvas.drawText(letter.toUpperCase(), this.x + xc, this.y + yc, textPaint)
            textPaint.color = textColor
        } else
            canvas.drawText(letter.toUpperCase(), this.x + xc, this.y + yc, textPaint)
    }

    override fun toString(): String = letter
}
