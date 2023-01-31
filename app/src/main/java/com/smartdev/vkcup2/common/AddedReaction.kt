package com.smartdev.vkcup2.common

import androidx.compose.animation.*
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*
import com.smartdev.vkcup2.ui.theme.FillUnSelected
import com.smartdev.vkcup2.util.AnimateEasing.EaseInBack
import com.smartdev.vkcup2.util.AnimateEasing.EaseOutBack
import com.smartdev.vkcup2.util.Emoji
import kotlinx.coroutines.launch


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AddedReaction(
    modifier: Modifier = Modifier,
    emoji: Emoji,
    reactionCount: Int,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {

    val interactionSource = remember { MutableInteractionSource() }

    val coroutine = rememberCoroutineScope()
    val scale = remember { Animatable(initialValue = 1f) }

    var iterations by remember { mutableStateOf(1) }

    LaunchedEffect(key1 = reactionCount) {
        if (isSelected) iterations = if (iterations == 1) 2 else 1
    }
    val animBg by animateColorAsState(
        targetValue = if (isSelected) Color(0xffDA6342) else FillUnSelected,
        tween(400)
    )

    val composition =
        rememberLottieComposition(
            spec = LottieCompositionSpec.RawRes(emoji.res),
        )
    Row(
        modifier = modifier
            .scale(scale.value)
            .clip(CircleShape)
            .background(animBg)
            .clickable(
                interactionSource = interactionSource,
                indication = rememberRipple(),
                onClick = {
                    onClick()
                    coroutine.launch {
                        scale.animateTo(0.8f, animationSpec = tween(300, easing = EaseInBack))
                        scale.animateTo(1f, animationSpec = tween(300, easing = EaseOutBack))

                    }
                }
            )
            .padding(start = 2.dp, top = 2.dp, bottom = 2.dp, end = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {


        LottieAnimation(
            modifier = Modifier
                .height(24.dp)
                .aspectRatio(1f)
                .clip(CircleShape),
            composition = composition.value,
            iterations = iterations
        )
        AnimatedContent(
            modifier = modifier,
            targetState = reactionCount,
            transitionSpec = {
                val direction =
                    if (initialState < targetState) AnimatedContentScope.SlideDirection.Up
                    else AnimatedContentScope.SlideDirection.Down
                slideIntoContainer(
                    towards = direction,
                    animationSpec = tween(300)
                ) with slideOutOfContainer(
                    towards = direction,
                    animationSpec = tween(300)
                )
            }
        ) {
            Text(
                text = reactionCount.toString(),
                style = MaterialTheme.typography.subtitle1,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}