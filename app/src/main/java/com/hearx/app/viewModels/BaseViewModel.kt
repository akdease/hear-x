package com.hearx.app.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {

    val navigateToClass = MutableLiveData<Class<*>>()
    val finishActivity = MutableLiveData<Boolean>()

    fun navigateToClass(theClass: Class<*>) {
        navigateToClass.postValue(theClass)
    }

    fun finishActivity() {
        finishActivity.postValue(true)
    }

}