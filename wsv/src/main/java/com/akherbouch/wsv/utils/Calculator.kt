package com.akherbouch.wsv.utils

class Calculator(width: Int, height: Int, private val numColumns: Int, private val numRows: Int) {

    val length: Int = Math.min(width / numColumns, height / numRows)
    val padX: Int = (width - length * numColumns) / 2
    val padY: Int = (height - length * numRows) / 2

    fun getColFromX(x: Float): Int = Math.floor(((x - padX) / length).toDouble()).toInt()

    fun getRowFromY(y: Float): Int = Math.floor(((y - padY) / length).toDouble()).toInt()

    fun isOutOfBounds(x: Int, y: Int): Boolean = x < 0 || x > numColumns - 1 || y < 0 || y > numRows - 1

}
