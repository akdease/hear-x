package com.hearx.app.activities

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.hearx.app.R
import com.hearx.app.config.HearxApplication
import com.hearx.app.databinding.ActivityMainBinding
import com.hearx.app.viewModels.MainViewModel
import javax.inject.Inject

class MainActivity : BaseActivity() {
    @Inject
    lateinit var mainViewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as HearxApplication).applicationComponent.inject(this)

        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewmodel = mainViewModel

        observeNavigationChanges(mainViewModel, this, this)
    }
}