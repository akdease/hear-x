package com.hearx.app.viewModels

import com.hearx.app.activities.ResultsActivity
import com.hearx.app.activities.TestActivity

class MainViewModel : BaseViewModel() {

    fun viewResults() {
        navigateToClass(ResultsActivity::class.java)
    }

    fun startTest() {
        navigateToClass(TestActivity::class.java)
    }
}