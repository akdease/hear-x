package com.hearx.app.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import com.hearx.app.viewModels.BaseViewModel

open class BaseActivity : AppCompatActivity() {
    fun observeNavigationChanges(
        baseViewModel: BaseViewModel,
        lifecycleOwner: LifecycleOwner,
        context: Context
    ) {
        baseViewModel.navigateToClass.observe(this) {
            if (it != null)
                startActivity(Intent(context, it))
        }
        baseViewModel.finishActivity.observe(this) {
            if (it)
                finish()
        }
    }
}