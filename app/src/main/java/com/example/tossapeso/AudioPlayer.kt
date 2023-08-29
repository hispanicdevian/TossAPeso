package com.example.tossapeso

import android.content.Context
import android.media.MediaPlayer

class AudioPlayer(private val context: Context, private val audioResource: Int) : AudioPlayback {

    private var mediaPlayer: MediaPlayer? = null

    override fun startAudio() {
        mediaPlayer = MediaPlayer.create(context, audioResource)
        mediaPlayer?.start()
    }

    override fun stopAudio() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}