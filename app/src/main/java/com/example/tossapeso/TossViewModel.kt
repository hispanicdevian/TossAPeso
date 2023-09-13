package com.example.tossapeso

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

sealed class AnimationState {
    object Initial : AnimationState()
    object Flipping : AnimationState()
    data class Finished(val result: Int) : AnimationState()
}

interface AudioPlayback {
    fun startAudio()
    fun stopAudio()
}

class TossViewModel(application: Context) : ViewModel(), AudioPlayback {

    private val audioPlayer: AudioPlayer = AudioPlayer(application, R.raw.coinflipsound20)

    val animationState = mutableStateOf<AnimationState>(AnimationState.Initial)
    val coinFlipResult = MutableLiveData<Int>()
    // LiveData or State to handle click event

    fun onAnimationFinished(result: Int) {
        animationState.value = AnimationState.Finished(result)
    }

    override fun startAudio() {
        viewModelScope.launch {
            audioPlayer.startAudio()
        }
    }

    override fun stopAudio() {
        viewModelScope.launch {
            audioPlayer.stopAudio()
        }
    }
}