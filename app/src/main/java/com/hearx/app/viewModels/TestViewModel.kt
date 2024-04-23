package com.hearx.app.viewModels

import android.content.Context
import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.MutableLiveData
import com.hearx.app.R
import com.hearx.app.activities.MainActivity
import com.hearx.app.activities.TestActivity
import com.hearx.app.database.ResultData
import com.hearx.app.database.ResultsDatabase
import com.hearx.app.utilities.AppConstants
import com.hearx.app.models.Raw
import com.hearx.app.models.RoundData
import com.hearx.app.models.TestData
import com.hearx.app.repositories.TestRepository
import com.hearx.app.utilities.AppConstants.Companion.RANDOM_NUMBER_END_INDEX
import com.hearx.app.utilities.AppConstants.Companion.RANDOM_NUMBER_START_INDEX
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class TestViewModel @Inject constructor(private val testRepository: TestRepository) :
    BaseViewModel() {
    lateinit var context: Context

    private val rounds = mutableListOf<RoundData>()
    private val isFirstRound = MutableLiveData(true)

    val currentRound = MutableLiveData(0)
    val currentRoundText = MutableLiveData<String>()

    val difficulty = MutableLiveData(AppConstants.DIFFICULTY_DEFAULT)
    val difficultyText = MutableLiveData<String>()

    val showScoreDialog = MutableLiveData(false)

    val digit_1 = MutableLiveData(0)
    val digit_2 = MutableLiveData(0)
    val digit_3 = MutableLiveData(0)

    val tripletAnswer = MutableLiveData<String>("")
    val tripletError = MutableLiveData<String>()

    val playNoise = MutableLiveData<Int>()
    val playDigit = MutableLiveData<Int>()
    val playTriplet = MutableLiveData<Boolean>()

    val isLoading = MutableLiveData<Boolean>()

    @MainThread
    fun exitTest() {
        (context as TestActivity).finish()
    }

    @MainThread
    fun submitTest() {
        validateInput()
    }

    @MainThread
    fun playNoise() {
        playNoise.postValue(difficulty.value!!)
    }

    @MainThread
    fun digit1OnClick() {
        playDigit(digit_1.value!!)
    }

    @MainThread
    fun digit2OnClick() {
        playDigit(digit_2.value!!)
    }

    @MainThread
    fun digit3OnClick() {
        playDigit(digit_3.value!!)
    }

    fun getScore(): Int {
        var sum = 0
        rounds.forEach {
            sum += it.difficulty
        }
        return sum
    }

    fun updateRoundText() {
        currentRoundText.value =
            String.format(context.getString(R.string.round), currentRound.value)
    }

    fun updateDifficultyText() {
        difficultyText.value =
            String.format(context.getString(R.string.difficulty), difficulty.value)
    }

    fun updateTriplets() {
        digit_1.value = getUniqueRandomNumber(1)
        digit_2.value = getUniqueRandomNumber(2)
        digit_3.value = getUniqueRandomNumber(3)
    }

    fun goToNextRound() {
        isLoading.value = true
        currentRound.value = (currentRound.value ?: 0) + 1

        resetRound()
        updateRoundText()
        updateDifficultyText()
        updateTriplets()

        isLoading.value = false
        playTriplet()
    }

    private fun getUniqueRandomNumber(digit: Int): Int {
        var randomNumber = (RANDOM_NUMBER_START_INDEX..RANDOM_NUMBER_END_INDEX).random()

        val previousRound =
            if (currentRound.value != null && rounds.isNotEmpty() && currentRound.value!! >= 2)
                rounds[currentRound.value!! - 2] else null

        var previous_digit_1 = 0
        var previous_digit_2 = 0
        var previous_digit_3 = 0

        if (previousRound != null) {
            val triplet = previousRound.triplet_answered

            previous_digit_1 = triplet.substring(0, 1).toInt()
            previous_digit_2 = triplet.substring(1, 2).toInt()
            previous_digit_3 = triplet.substring(2, 3).toInt()
        }

        return getFinalRandomNumber(
            digit,
            randomNumber,
            previous_digit_1,
            previous_digit_2,
            previous_digit_3
        )
    }

    private fun getFinalRandomNumber(
        digit: Int,
        random: Int,
        previous_digit_1: Int,
        previous_digit_2: Int,
        previous_digit_3: Int
    ): Int {
        var randomNumber = random

        while (when (digit) {
                1 -> randomNumber == previous_digit_2 || randomNumber == previous_digit_3
                2 -> randomNumber == previous_digit_1 || randomNumber == previous_digit_3
                3 -> randomNumber == previous_digit_1 || randomNumber == previous_digit_2
                else -> (when (digit) {
                    1 -> randomNumber == digit_2.value || randomNumber == digit_3.value
                    2 -> randomNumber == digit_1.value || randomNumber == digit_3.value
                    3 -> randomNumber == digit_1.value || randomNumber == digit_2.value
                    else -> false
                })
            }
        ) {
            randomNumber = (RANDOM_NUMBER_START_INDEX..RANDOM_NUMBER_END_INDEX).random()
        }

        return randomNumber
    }

    private fun playTriplet() {
        playTriplet.postValue(true)
    }

    private fun playDigit(digit: Int) {
        playDigit.postValue(digit)
    }

    private fun validateInput() {
        when {
            (tripletAnswer.value?.length ?: 0) < 3 -> {
                val error = context.getString(R.string.test_error_minimum_characters)
                if (error != tripletError.value)
                    tripletError.value = error
            }

            tripletAnswer.value == "000" -> {
                val error = context.getString(R.string.test_error_invalid_characters)
                if (error != tripletError.value)
                    tripletError.value = error
            }

            else -> {
                if (tripletError.value != null)
                    tripletError.value = null

                recordRound()
                setDifficulty()
                finaliseRound()
            }
        }
    }

    private fun setDifficulty() {
        if (isAnswerCorrect())
            difficulty.value =
                if ((difficulty.value ?: 0) < AppConstants.MAX_ROUNDS) (difficulty.value
                    ?: 0) + 1 else difficulty.value
        else
            difficulty.value = if ((difficulty.value ?: 0) > 1) (difficulty.value
                ?: 0) - 1 else difficulty.value
    }

    private fun isAnswerCorrect(): Boolean {
        return tripletAnswer.value == "${digit_1.value}${digit_2.value}${digit_3.value}"
    }

    private fun recordRound() {
        val roundData = RoundData(
            difficulty.value!!,
            "${digit_1.value}${digit_2.value}${digit_3.value}",
            tripletAnswer.value ?: ""
        )
        rounds.add(roundData)
    }

    private fun resetRound() {
        playNoise.postValue(null)
        playDigit.postValue(null)
        playTriplet.postValue(null)
        tripletAnswer.postValue("")
    }

    private fun finaliseRound() {
        if ((currentRound.value ?: 0) < AppConstants.MAX_ROUNDS)
            goToNextRound()
        else
            uploadData()
    }

    private fun uploadData() {
        isLoading.postValue(true)

        val score = getScore()
        val testData = TestData("raw", Raw(score, rounds))

        CoroutineScope(Dispatchers.Default).launch {
            testRepository.postTestData(testData)
            saveToDatabase()

            isLoading.postValue(false)
            showCompleteDialog()
        }
    }

    private fun saveToDatabase() {
        CoroutineScope(Dispatchers.Default).launch {
            val database = ResultsDatabase.getDatabase(context)
            val result = ResultData(score = getScore(), difficulty = difficulty.value!!)
            database.resultsDao().insert(result)
            Log.i(this.javaClass.canonicalName, "Saved to Room DB")
        }
    }

    private fun showCompleteDialog() {
        showScoreDialog.postValue(true)
    }
}