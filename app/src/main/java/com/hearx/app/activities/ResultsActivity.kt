package com.hearx.app.activities

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.hearx.app.R
import com.hearx.app.config.MyApplication
import com.hearx.app.databinding.ActivityResultsBinding
import com.hearx.app.viewModels.ResultsViewModel
import javax.inject.Inject

class ResultsActivity : BaseActivity() {
    @Inject
    lateinit var resultsViewModel: ResultsViewModel

    private lateinit var binding: ActivityResultsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as MyApplication).applicationComponent.inject(this)

        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_results)
        binding.lifecycleOwner = this
    }
}