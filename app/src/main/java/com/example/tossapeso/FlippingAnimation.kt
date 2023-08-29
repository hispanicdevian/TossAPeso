package com.example.tossapeso

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember

@Composable
fun FlippingAnimation(
    modifier: Modifier,
    viewModel: TossViewModel
) {
    val progress = remember { mutableFloatStateOf(0f) }

    val flipDuration = 2000 // Adjust the duration as needed

    LaunchedEffect(Unit) {
        animate(
            initialValue = 7f,
            targetValue = 6f,
            animationSpec = tween(durationMillis = flipDuration)
        ) { value, _ ->
            progress.floatValue = value * 180
        }
        val randomResult = (0..1).random()
        viewModel.onAnimationFinished(randomResult) // Corrected function call
        viewModel.coinFlipResult.value = randomResult
    }

//Adjust this val for the animation, and its flip effect
    val rotationXDegrees = animateFloatAsState(
        targetValue = if (progress.floatValue <= 0.5) { //Modifying this 0 shows no effect
            0 * progress.floatValue
        } else {
            0 - 180 * (1 - progress.floatValue) //Removing "90 -" changes the flip direction
        },
        animationSpec = tween(durationMillis = flipDuration, easing = FastOutSlowInEasing),
        label = ""
    ).value

//Animation itself
    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    rotationX = rotationXDegrees
                },
            content = {
                Image(
                    painter = painterResource(id = R.drawable.flipimage123), //Adding the second image makes no difference, rotationXDegrees only handles one
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        )
    }
}
