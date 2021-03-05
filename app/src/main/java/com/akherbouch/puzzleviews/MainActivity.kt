package com.akherbouch.puzzleviews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.annotation.RawRes
import androidx.annotation.StringRes
import com.akherbouch.wsv.WordSearchPuzzle
import com.akherbouch.wsv.WordSearchView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val puzzle = WordSearchPuzzle (
            rows = 7, columns = 5,
            letters = listOf (
                "H","A","B","D","D",
                "M","E","C","O","S",
                "F","B","L","N","U",
                "A","U","O","L","P",
                "P","V","C","A","O",
                "W","O","R","L","D",
                "C","A","O","T","S"
            ),
            answers = listOf (
                "0,0:4,4",
                "0,5:4,5"
            )
        )
        wordSearchView.setup(puzzle)
        wordSearchView.registerLifecycle(this.lifecycle)
        wordSearchView.setCallBacks(object : WordSearchView.CallBacks {

            override fun onWin(view: WordSearchView) = showToast(R.string.onWin)
            override fun onSolve(view: WordSearchView) =  showToast(R.string.onSolve)
            override fun onSelect(view: WordSearchView, word: String?) = Unit
            override fun onHint(view: WordSearchView) = Unit

        })
    }

    private fun showToast(@StringRes id: Int) {
        Toast.makeText(this, getString(id), Toast.LENGTH_SHORT).show()
    }

}