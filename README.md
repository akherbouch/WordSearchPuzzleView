# WordSearchPuzzleView
<p align="center">
  <a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
  <a href="https://android-arsenal.com/api?level=16"><img alt="API" src="https://img.shields.io/badge/API-16%2B-brightgreen.svg?style=flat"/></a>

</p>

<p align="center">
🧩 An easy way to make you own <b>word search game</b> for Android 🧩
</p>

<p align="center">
<img src="https://user-images.githubusercontent.com/35242313/110139632-89e52780-7dd3-11eb-9afe-9a17390adb42.png" width="700" height="420"/>
</p>

## Usage



<img src="https://user-images.githubusercontent.com/35242313/110142419-6f607d80-7dd6-11eb-84e4-e65f19408d75.gif" align="right" width="32%"/>

Here is a simple example of implementing WordSearchPuzzleView on MainActivity
```kotlin
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
```


# License
```xml
Copyright 2019 akherbouch (Ayoub Kherbouch)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```



