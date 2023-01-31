package com.smartdev.vkcup2.ui.screens.reaction

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.smartdev.vkcup2.R
import com.smartdev.vkcup2.common.AddedReaction
import com.smartdev.vkcup2.ui.theme.FillSelected
import com.smartdev.vkcup2.ui.theme.MainBackground
import com.smartdev.vkcup2.util.Emoji
import com.smartdev.vkcup2.util.horizontalSpacer
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ReactionScreen(
    viewModel: ReactionViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()

    ) {
        itemsIndexed(items = uiState.article) { index, item ->
            val showReactionIcons = remember {
                mutableStateOf(false)
            }
            Divider(color = Color.White.copy(0.1f))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Image(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape),
                        painter = painterResource(id = R.drawable.vk_header),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                    Column() {
                        Text(
                            text = "Название канала",
                            style = MaterialTheme.typography.body2,
                            color = Color.White,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = "15.4K  подписчиков",
                            style = MaterialTheme.typography.caption,
                            color = Color.White
                        )
                    }

                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        modifier = Modifier,
                        painter = painterResource(id = R.drawable.ic_check_circle_48px),
                        contentDescription = null,
                        tint = Color(0xffDA6342)
                    )
                    Text(
                        text = "Подписаться",
                        style = MaterialTheme.typography.subtitle2,
                        color = Color(0xffDA6342)
                    )
                }
            }
            Text(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 4.dp),
                text = item.title,
                style = MaterialTheme.typography.h6
            )
            Text(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
                text = item.subtitle,
                style = MaterialTheme.typography.body2
            )
            Box {
                val showEmojiOnFullScreen = remember { mutableStateOf(false) }
                val emojiOnFullScreen = remember { mutableStateOf(Emoji.Emoji1) }

                item.image.forEach {
                    Image(
                        modifier = Modifier
                            .fillMaxWidth(),
                        painter = painterResource(id = it),
                        contentDescription = null,
                        contentScale = ContentScale.Fit
                    )
                }
                AnimatedVisibility(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(end = 16.dp, bottom = 4.dp),
                    visible = showReactionIcons.value,
                    enter = slideInVertically { it } + fadeIn(),
                    exit = slideOutVertically { it } + fadeOut()
                ) {
                    Column(
                        modifier = Modifier
                            .padding(4.dp)
                            .width(40.dp)
                            .clip(CircleShape)
                            .background(MainBackground),
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        repeat(4) {
                            val emoji = remember { Emoji.values()[it] }
                            val interactionSource = remember { MutableInteractionSource() }
                            val composition =
                                rememberLottieComposition(spec = LottieCompositionSpec.RawRes(emoji.res))
                            val animBg by
                            animateColorAsState(
                                targetValue = if (emoji == item.selectedReaction?.emoji) FillSelected else Color.Transparent,
                                animationSpec = tween(400)
                            )
                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(animBg)
                            ) {
                                LottieAnimation(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .aspectRatio(1f)
                                        .combinedClickable(
                                            interactionSource = interactionSource,
                                            indication = rememberRipple(radius = 20.dp),
                                            onLongClick = {
                                                showEmojiOnFullScreen.value = true
                                                emojiOnFullScreen.value = emoji
                                                showReactionIcons.value = false
                                            },
                                            onClick = {
                                                viewModel.reactPublication(
                                                    publicationPos = index,
                                                    reaction = emoji
                                                )
                                                showReactionIcons.value = false

                                            },
                                        )
                                        .clip(CircleShape),
                                    composition = composition.value,
                                    iterations = LottieConstants.IterateForever
                                )
                            }
                        }
                    }
                }
                AnimatedVisibility(
                    modifier = Modifier.align(Alignment.Center),
                    visible = showEmojiOnFullScreen.value,
                    enter = fadeIn(animationSpec = tween(600)),
                    exit = fadeOut(animationSpec = tween(600))
                ) {
                    val composition =
                        rememberLottieComposition(
                            spec = LottieCompositionSpec.RawRes(emojiOnFullScreen.value.res)
                        )
                    LaunchedEffect(key1 = composition.isComplete) {
                        if (composition.isComplete) {
                            delay(1500)
                            showEmojiOnFullScreen.value = false
                            viewModel.reactPublication(
                                publicationPos = index,
                                reaction = emojiOnFullScreen.value
                            )
                        }
                    }
                    LottieAnimation(
                        modifier = Modifier
                            .fillMaxWidth(0.4f)
                            .aspectRatio(1f)
                            .clip(CircleShape),
                        composition = composition.value,
                        iterations = 1,

                        )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                item.reaction.forEach { reaction ->
                    AddedReaction(
                        emoji = reaction.emoji,
                        reactionCount = reaction.count,
                        onClick = { viewModel.reactPublication(index, reaction.emoji) },
                        isSelected = reaction.isSelected
                    )
                    horizontalSpacer(width = 5.dp)
                }
                horizontalSpacer(width = 5.dp)
                IconButton(onClick = { showReactionIcons.value = !showReactionIcons.value }) {
                    Icon(
                        modifier = Modifier,
                        painter = painterResource(id = R.drawable.outline_add_reaction_24),
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }

            Divider(color = Color.White.copy(0.1f))
        }
    }
}