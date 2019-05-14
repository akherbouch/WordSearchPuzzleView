package com.akherbouch.puzzleviews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.akherbouch.wsv.WordSearchPuzzle
import com.akherbouch.wsv.WordSearchView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        wordSearchView.registerLifecycle(this.lifecycle)
        wordSearchView.setCallBacks(object : WordSearchView.CallBacks {

            override fun onWin(view: WordSearchView) = Unit
            override fun onSolve(view: WordSearchView) = Unit
            override fun onSelect(view: WordSearchView, word: String?) = Unit
            override fun onHint(view: WordSearchView) {
               Log.e("wsv","--------> hint")
            }

        })
    }
}
