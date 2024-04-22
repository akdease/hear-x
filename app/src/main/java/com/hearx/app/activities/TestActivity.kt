package com.hearx.app.activities

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.hearx.app.R
import com.hearx.app.config.MyApplication
import com.hearx.app.databinding.ActivityTestBinding
import com.hearx.app.viewModels.TestViewModel
import javax.inject.Inject

class TestActivity : BaseActivity() {
    @Inject
    lateinit var testViewModel: TestViewModel
    private lateinit var binding: ActivityTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as MyApplication).applicationComponent.inject(this)

        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_test)
        binding.lifecycleOwner = this
        binding.activity = this
        binding.viewmodel = testViewModel
    }

    fun getFormattedRoundString(): String {
        val res = getResources()
        return String.format(res.getString(R.string.round), testViewModel.currentRound.value)
    }
}