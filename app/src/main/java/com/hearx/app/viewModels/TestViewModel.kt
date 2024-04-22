package com.hearx.app.viewModels

import androidx.lifecycle.MutableLiveData
import com.hearx.app.utilities.AppConstants
import com.hearx.app.models.Raw
import com.hearx.app.models.RoundData
import com.hearx.app.models.TestData
import com.hearx.app.repositories.TestRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TestViewModel @Inject constructor(private val testRepository: TestRepository) :
    BaseViewModel() {

    private val rounds = mutableListOf<RoundData>()
    private val isFirstRound = MutableLiveData(true)
    private val difficulty = MutableLiveData(5)

    val currentRound = MutableLiveData(1)

    val digit1 = MutableLiveData(0)
    val digit2 = MutableLiveData(0)
    val digit3 = MutableLiveData(0)

    val playAudio = MutableLiveData<String>()

    fun submitTest() {
        val roundData = RoundData(
            difficulty.value!!,
            "123",
            "123"
        )
        rounds.add(roundData)

        if ((currentRound.value ?: 0) < AppConstants.MAX_ROUNDS)
            currentRound.value = (currentRound.value ?: 0) + 1
        else {
            currentRound.value = 1
            uploadData()
        }
    }

    fun playNoise() {
        playAudio.postValue("noise_${currentRound.value}.m4a")
    }

    fun digit1() {
        playRound(1)
    }

    fun digit2() {
        playRound(2)
    }

    fun digit3() {
        playRound(3)
    }

    private fun playRound(digit: Int) {
        playAudio.postValue("${digit}.m4a")
    }

    private fun getUniqueRandomNumber(): Int {
        val randomNumber = (0..10).random()
        return randomNumber
    }

    private fun uploadData() {
        val score = getScore()
        val testData = TestData("raw", Raw(score, rounds))
        CoroutineScope(Dispatchers.Default).launch {
            val response = testRepository.postTestData(testData)

            withContext(Dispatchers.Main) {
                //
            }
        }
    }

    private fun getScore() : Int {
        return 0
    }
}