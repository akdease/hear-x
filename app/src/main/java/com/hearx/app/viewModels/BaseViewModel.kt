package com.hearx.app.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {

    val navigateToClass = MutableLiveData<Class<*>>()
    val finishActivity = MutableLiveData<Boolean>()

    fun navigateToClass(theClass: Class<*>) {
        navigateToClass.value = theClass
    }

    fun finishActivity() {
        finishActivity.value = true
    }

}