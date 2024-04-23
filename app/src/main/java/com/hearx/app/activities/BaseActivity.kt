package com.hearx.app.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import com.hearx.app.viewModels.BaseViewModel

open class BaseActivity : AppCompatActivity() {
    lateinit var baseViewModel: BaseViewModel
    lateinit var lifecycleOwner: LifecycleOwner
    lateinit var context: Context

    fun observeNavigationChanges(
        baseViewModel: BaseViewModel,
        lifecycleOwner: LifecycleOwner,
        context: Context
    ) {
        this.baseViewModel = baseViewModel
        this.lifecycleOwner = lifecycleOwner
        this.context = context

        baseViewModel._navigateToClass.observe(lifecycleOwner) {
            navigateToClassObserver(it, context)
        }
        baseViewModel._finishActivity.observe(lifecycleOwner) {
            finishActivityObserver(it)
        }
    }

    private fun navigateToClassObserver(it: Class<*>, context: Context) {
        if (it != null)
            startActivity(Intent(context, it))
    }

    private fun finishActivityObserver(it: Boolean) {
        if (it)
            finish()
    }
}