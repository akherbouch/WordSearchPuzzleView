package com.akherbouch.wsv.drawables

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import com.akherbouch.wsv.R

class Background(res: Resources, width: Int, height: Int, private val padX: Int, private val padY: Int) : Drawable() {

    private val h1: Int = width / 20
    private val h2: Int = height - 2 * h1
    private val parts = arrayOf (
        Bitmap.createScaledBitmap(BitmapFactory.decodeResource(res, R.drawable.cbg_top), width, h1, true),
        Bitmap.createScaledBitmap(BitmapFactory.decodeResource(res, R.drawable.cbg_center), width, h2, true),
        Bitmap.createScaledBitmap(BitmapFactory.decodeResource(res, R.drawable.cbg_bottom), width, h1, true)
    )

    override fun draw(canvas: Canvas) {
        canvas.drawBitmap(parts[0], padX.toFloat(), padY.toFloat(), null)
        canvas.drawBitmap(parts[1], padX.toFloat(), (padY + h1).toFloat(), null)
        canvas.drawBitmap(parts[2], padX.toFloat(), (padY + h1 + h2).toFloat(), null)
    }

}
