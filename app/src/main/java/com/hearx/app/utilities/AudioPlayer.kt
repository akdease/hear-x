package com.hearx.app.utilities

import android.content.Context
import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer

class AudioPlayer {

    private var mediaPlayer = MediaPlayer()
    fun playAudio(fileName: String, context: Context) {
        stopAudio()

        val descriptor: AssetFileDescriptor = context.assets.openFd(fileName)
        mediaPlayer.setDataSource(
            descriptor.fileDescriptor,
            descriptor.startOffset,
            descriptor.getLength()
        )
        descriptor.close()

        mediaPlayer.prepare()
        mediaPlayer.setVolume(1f, 1f)
        mediaPlayer.isLooping = false
        mediaPlayer.start()
    }

    fun stopAudio() {
        mediaPlayer.stop()
        mediaPlayer.release()
        mediaPlayer = MediaPlayer()
    }
}