package com.akherbouch.wsv.utils

object ExceptionUtil {

    @JvmStatic
    fun createLetterLessThanCasesException(): RuntimeException {
        return RuntimeException("number of letters is less than number of cases, check setup function parameters")
    }
}
