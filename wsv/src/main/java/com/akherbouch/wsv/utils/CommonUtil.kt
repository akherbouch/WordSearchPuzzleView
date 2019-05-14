package com.akherbouch.wsv.utils

object CommonUtil {

    @JvmStatic
    fun randomIndexesWithKey(size: Int, key: String): IntArray {
        val indexes = IntArray(size)
        val first = key.hashCode() % size
        val step = key.hashCode() / size % size

        for (i in 0 until size) indexes[i] = i

        for (j in 0 until size) {
            val i = (first + step * j + size) % size
            val temp = indexes[i]
            indexes[i] = indexes[j]
            indexes[j] = temp

        }

        return indexes
    }

    @JvmStatic
    fun isDiag(c1: Int, r1: Int, c2: Int, r2: Int): Boolean {
        if (c1 == c2) return true
        if (r1 == r2) return true
        return Math.abs(c2 - c1) == Math.abs(r2 - r1)
    }

    @JvmStatic
    fun compare(a: Int, b: Int): Int {
        if (a > b) return 1
        else if (a < b) return -1
        return 0
    }

    @JvmStatic
    fun sleep(millis: Long) {
        try {
            Thread.sleep(millis)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}
