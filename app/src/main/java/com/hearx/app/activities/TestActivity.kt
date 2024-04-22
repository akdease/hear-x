package com.hearx.app.activities

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.hearx.app.R
import com.hearx.app.config.HearxApplication
import com.hearx.app.databinding.ActivityTestBinding
import com.hearx.app.utilities.AudioPlayer
import com.hearx.app.viewModels.TestViewModel
import javax.inject.Inject

class TestActivity : BaseActivity() {
    @Inject
    lateinit var testViewModel: TestViewModel
    private lateinit var binding: ActivityTestBinding

    private val audioPlayer = AudioPlayer()

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as HearxApplication).applicationComponent.inject(this)

        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_test)
        binding.lifecycleOwner = this
        binding.activity = this
        binding.viewmodel = testViewModel

        observeNavigationChanges(testViewModel, this, this)
        observeLocalChanges()
    }

    override fun onDestroy() {
        audioPlayer.stopAudio()
        super.onDestroy()
    }

    private fun observeLocalChanges() {
        testViewModel.playAudio.observe(this) {
            if (!it.isNullOrEmpty())
                playAudio(it)
        }
        testViewModel.currentRound.observe(this) {
            audioPlayer.stopAudio()
        }
    }

    fun getFormattedRoundString(): String {
        val res = getResources()
        return String.format(res.getString(R.string.round), testViewModel.currentRound.value)
    }

    fun playAudio(fileName: String) {
        audioPlayer.playAudio(fileName, this)
    }
}