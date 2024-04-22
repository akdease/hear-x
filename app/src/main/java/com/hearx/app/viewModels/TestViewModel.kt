package com.hearx.app.viewModels

import androidx.lifecycle.MutableLiveData
import com.hearx.app.models.RoundData

class TestViewModel : BaseViewModel() {

    private val rounds = listOf<RoundData>()
    private val isFirstRound = MutableLiveData(true)
    private var difficulty = MutableLiveData(5)
    var currentRound = MutableLiveData(1)

    fun exitTest() {

    }

    fun submitTest() {

    }
}