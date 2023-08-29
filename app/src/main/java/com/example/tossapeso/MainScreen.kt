package com.example.tossapeso

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MainScreen() {
    val viewModel: TossViewModel = viewModel(factory = VmFactory(application = LocalContext.current.applicationContext))
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center // Align content in the middle
    ) {

        when (val animationState = viewModel.animationState.value) {
            AnimationState.Initial -> {
                CoinImage(
                    drawableId = R.drawable.tail123,
                    size = 180.dp,
                    onClick = {
                        viewModel.animationState.value = AnimationState.Flipping
                    },
                    audioPlayback = viewModel // Pass the ViewModel instance
                )
            }
            AnimationState.Flipping -> {
                FlippingAnimation(
                    modifier = Modifier
                        .size(180.dp), //Change this for Image size
                    viewModel = viewModel
                )
            }
            is AnimationState.Finished -> {
                ResultImage(
                    drawableId = if (animationState.result == 0) R.drawable.head123 else R.drawable.tail123,
                    size = 250.dp,
                    onClick = {
                        viewModel.animationState.value = AnimationState.Flipping
                    },
                    audioPlayback = viewModel // Pass the ViewModel instance
                )
            }
        }
    }
}

@Composable
fun CustomClickable(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    audioPlayback: AudioPlayback,
    content: @Composable () -> Unit
) {
    val gestureModifier = modifier.pointerInput(Unit) {
        detectTapGestures {
            audioPlayback.startAudio()
            onClick()
        }
    }

    Box(modifier = gestureModifier) {
        content()
    }
}

@Composable
fun CoinImage(drawableId: Int, size: Dp, onClick: () -> Unit, audioPlayback: TossViewModel) {
    CustomClickable(
        modifier = Modifier
            .size(size)
            .clip(MaterialTheme.shapes.medium),
        onClick = onClick,
        audioPlayback = audioPlayback
    ) {
        Image(
            painter = painterResource(id = drawableId),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun ResultImage(drawableId: Int, size: Dp, onClick: () -> Unit, audioPlayback: AudioPlayback) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures {
                    audioPlayback.startAudio()
                    onClick()
                }
            },
        contentAlignment = Alignment.Center // Align content in the middle
    ) {
        Image(
            painter = painterResource(id = drawableId),
            contentDescription = null,
            modifier = Modifier
                .size(size)
                .clip(MaterialTheme.shapes.medium),
            contentScale = ContentScale.Crop
        )
    }
}
