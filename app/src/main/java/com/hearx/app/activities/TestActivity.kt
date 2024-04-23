package com.hearx.app.activities

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.hearx.app.R
import com.hearx.app.config.HearxApplication
import com.hearx.app.databinding.ActivityTestBinding
import com.hearx.app.dialogs.HearXDialog
import com.hearx.app.utilities.AudioPlayer
import com.hearx.app.viewModels.TestViewModel
import javax.inject.Inject

class TestActivity : BaseActivity() {
    @Inject
    lateinit var testViewModel: TestViewModel
    private lateinit var binding: ActivityTestBinding

    private val noiseAudioPlayer = AudioPlayer()
    private val tripletAudioPlayer = AudioPlayer()

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as HearxApplication).applicationComponent.inject(this)

        super.onCreate(savedInstanceState)

        testViewModel.context = this
        testViewModel.goToNextRound()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_test)
        binding.lifecycleOwner = this
        binding.viewmodel = testViewModel

        observeLocalChanges()
    }

    private fun showScoreDialog() {
        HearXDialog(
            getString(R.string.test_complete),
            String.format(getString(R.string.test_score), testViewModel.getScore()),
            getString(R.string.done)
        ) { testViewModel.exitTest() }.show(supportFragmentManager, this.javaClass.canonicalName)
    }

    private fun observeLocalChanges() {
        testViewModel.playNoise.observe(this) {
            if (it != null)
                playNoise(it)
        }
        testViewModel.playDigit.observe(this) {
            if (it != null)
                playDigit(it)
        }
        testViewModel.playTriplet.observe(this) {
            if (it == true)
                playTriplet()
        }
        testViewModel.currentRound.observe(this) {
            stopAllAudio()
            playNoise(testViewModel.difficulty.value!!)
        }
        testViewModel.showScoreDialog.observe(this) {
            if (it)
                showScoreDialog()
        }
        testViewModel.tripletError.observe(this) {
            binding.txtInputLayout.error = it
        }
    }

    private fun playNoise(number: Int) {
        noiseAudioPlayer.playAudio("noise_${number}.m4a", this)
    }

    private fun playDigit(number: Int) {
        tripletAudioPlayer.playAudio("${number}.m4a", this)
    }

    private fun playTriplet() {
        tripletAudioPlayer.playAudio("${testViewModel.digit_1.value}.m4a", this)
        tripletAudioPlayer.getMediaPlayer().setOnCompletionListener {
            tripletAudioPlayer.playAudio("${testViewModel.digit_2.value}.m4a", this)
            tripletAudioPlayer.getMediaPlayer().setOnCompletionListener {
                tripletAudioPlayer.playAudio("${testViewModel.digit_3.value}.m4a", this)
                tripletAudioPlayer.getMediaPlayer().setOnCompletionListener {
                    noiseAudioPlayer.stopAudio()
                }
            }
        }
    }

    private fun stopAllAudio() {
        noiseAudioPlayer.stopAudio()
        tripletAudioPlayer.stopAudio()
    }
}