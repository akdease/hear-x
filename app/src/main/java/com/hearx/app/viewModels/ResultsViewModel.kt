package com.hearx.app.viewModels

import android.content.Context
import androidx.annotation.MainThread
import androidx.lifecycle.MutableLiveData
import com.hearx.app.activities.ResultsActivity
import com.hearx.app.database.ResultData
import com.hearx.app.database.ResultsDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ResultsViewModel : BaseViewModel() {
    lateinit var context: Context
    val results = MutableLiveData<List<ResultData>>()
    val isLoading = MutableLiveData<Boolean>()
    fun getResults() {
        val database = ResultsDatabase.getDatabase(context)

        CoroutineScope(Dispatchers.Default).launch {
            val resultsList = database.resultsDao().getAllResults()
            results.postValue(resultsList)
        }
    }

    @MainThread
    fun done() {
        (context as ResultsActivity).finish()
    }

}