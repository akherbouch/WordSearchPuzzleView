package com.akherbouch.wsv.utils

import android.content.Context
import android.graphics.Paint
import android.graphics.Typeface
import android.text.TextPaint
import androidx.core.content.res.ResourcesCompat

object DrawUtil {

    @JvmStatic
    fun createFillPaint(color: Int): Paint {
        val fillPaint = Paint(1)
        fillPaint.style = Paint.Style.FILL
        fillPaint.color = color
        fillPaint.isAntiAlias = true
        fillPaint.isDither = true
        return fillPaint
    }

    @JvmStatic
    fun createStrokePaint(color: Int, width: Float): Paint {
        val fillPaint = Paint(1)
        fillPaint.style = Paint.Style.STROKE
        fillPaint.color = color
        fillPaint.strokeWidth = width
        fillPaint.isAntiAlias = true
        fillPaint.isDither = true
        return fillPaint
    }

    @JvmStatic
    fun createTextPaintFromResources(context: Context, fontId: Int, size: Float, color: Int): TextPaint {
        val textPaint = TextPaint(1)
        val f = context.resources.displayMetrics.density
        textPaint.textSize = size * f
        textPaint.isAntiAlias = true
        textPaint.isDither = true
        val typeface = ResourcesCompat.getFont(context, fontId)
        textPaint.typeface = typeface
        textPaint.color = color
        return textPaint
    }


}