package com.hearx.app.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {

    val _navigateToClass = MutableLiveData<Class<*>>()
    val _finishActivity = MutableLiveData<Boolean>()

    fun navigateToClass(theClass: Class<*>) {
        _navigateToClass.postValue(theClass)
    }

    fun finishActivity() {
        _finishActivity.postValue(true)
    }

}