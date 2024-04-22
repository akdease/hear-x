package com.hearx.app.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import com.hearx.app.config.MyApplication
import com.hearx.app.viewModels.BaseViewModel
import javax.inject.Inject

open class BaseActivity : AppCompatActivity() {
    @Inject
    lateinit var baseViewModel: BaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as MyApplication).applicationComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    fun observeChanges(lifecycleOwner: LifecycleOwner, context: Context) {
        baseViewModel.navigateToClass.observe(lifecycleOwner) {
            if (it != null)
                startActivity(Intent(context, it))
        }
        baseViewModel.finishActivity.observe(lifecycleOwner) {
            if (it)
                finish()
        }
    }
}