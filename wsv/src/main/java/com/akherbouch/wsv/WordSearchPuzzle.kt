package com.akherbouch.wsv

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize


@Keep
@Parcelize
open class WordSearchPuzzle @JvmOverloads constructor (
    var rows: Int = 2,
    var columns: Int = 2,
    var letters: List<String> = listOf("A","B","C","D"),
    var answers: List<String> = listOf("0,0:1,1"),
    var state: String = ""
) : Parcelable