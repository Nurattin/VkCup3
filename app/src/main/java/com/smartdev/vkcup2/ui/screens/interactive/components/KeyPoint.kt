package com.smartdev.vkcup2.ui.screens.interactive.components

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp


@Composable
fun KeyPoint(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    @DrawableRes icon: Int,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .size(24.dp)
            .clip(CircleShape)
            .background(Color.White.copy(0.1f)),
        contentAlignment = Alignment.Center
    ) {
        val animSize by animateDpAsState(targetValue = if (isSelected) 24.dp else 20.dp)
        Image(
            modifier = Modifier
                .size(animSize)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(radius = animSize / 2),
                    onClick = onClick
                ),
            painter = painterResource(id = icon),
            contentDescription = null
        )
    }
}