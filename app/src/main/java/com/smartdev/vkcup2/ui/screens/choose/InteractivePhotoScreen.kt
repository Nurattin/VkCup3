package com.smartdev.vkcup2.ui.screens.choose

import android.media.MediaPlayer
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.smartdev.vkcup2.R
import com.smartdev.vkcup2.ui.theme.MainBackground


@Composable
fun InteractivePhotoScreen(
    modifier: Modifier = Modifier,
    onClickChooseBtn: (Int) -> Unit,
    viewModel: InteractivePhotoViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {

    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()

    ) {
        items(items = uiState.article) {
            Divider(color = Color.White.copy(0.1f))
            Row(
                modifier = modifier
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
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
                text = it.title,
                style = MaterialTheme.typography.body1
            )
            it.image.forEach {
                var showKeyPoint by remember {
                    mutableStateOf(false)
                }
                Box {
                    Image(
                        modifier = modifier
                            .fillMaxWidth(),
                        painter = painterResource(id = it.src),
                        contentDescription = null,
                        contentScale = ContentScale.Fit
                    )
                    if (showKeyPoint) {
                        it.keyPoint.forEach { ketPoint ->
                            when (ketPoint.type) {
                                is Type.Content -> {
                                    var dropIsShow by remember {
                                        mutableStateOf(false)
                                    }
                                    Box(modifier = Modifier
                                        .offset(
                                            ketPoint.offset.first.dp,
                                            ketPoint.offset.second.dp
                                        )
                                        .clickable {
                                            dropIsShow = true
                                        }) {

                                        if (dropIsShow) {
                                            Popup(
                                                onDismissRequest = { dropIsShow = false },
                                                properties = PopupProperties(
                                                    focusable = true,
                                                    dismissOnBackPress = false
                                                ),
                                            ) {
                                                val animateSize =
                                                    remember { Animatable(initialValue = 0f) }
                                                LaunchedEffect(key1 = Unit) {
                                                    animateSize.animateTo(
                                                        1f,
                                                        animationSpec = tween(500)
                                                    )
                                                }
                                                Box(
                                                    modifier = Modifier
                                                        .shadow(
                                                            3.dp,
                                                            clip = true,
                                                            ambientColor = Color.White
                                                        )
                                                        .size((animateSize.value * 200).dp)
                                                        .background(MainBackground)
                                                        .padding(4.dp)
                                                        .animateContentSize()
                                                ) {
                                                    Column(
                                                        modifier = Modifier.fillMaxSize(),
                                                        verticalArrangement = Arrangement.spacedBy(4.dp)
                                                    ) {
                                                        Image(
                                                            modifier = Modifier
                                                                .fillMaxWidth()
                                                                .aspectRatio(3 / 2f)
                                                                .clip(
                                                                    RoundedCornerShape(
                                                                        bottomEnd = 8.dp,
                                                                        bottomStart = 8.dp
                                                                    )
                                                                ),
                                                            painter = painterResource(id = ketPoint.type.banner),
                                                            contentDescription = null,
                                                            contentScale = ContentScale.Crop
                                                        )
                                                        Text(
                                                            text = ketPoint.type.title,
                                                            color = Color.White,
                                                            style = MaterialTheme.typography.caption
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                        val transition = rememberInfiniteTransition()
                                        val size = transition.animateFloat(
                                            initialValue = 10f,
                                            targetValue = 50f,
                                            animationSpec = infiniteRepeatable(
                                                animation = tween(2000),
                                                repeatMode = RepeatMode.Restart
                                            )
                                        )
                                        val color = transition.animateColor(
                                            initialValue = Color.White,
                                            targetValue = Color.Transparent,
                                            animationSpec = infiniteRepeatable(
                                                animation = tween(2000),
                                                repeatMode = RepeatMode.Restart
                                            )
                                        )
                                        Canvas(
                                            modifier = Modifier

                                        ) {
                                            drawCircle(
                                                color = Color.White, radius = 10f
                                            )
                                            drawCircle(
                                                color = Color.White,
                                                radius = 20f,
                                                style = Stroke(1f)
                                            )
                                            drawCircle(
                                                color = color.value, radius = size.value
                                            )
                                        }
                                    }
                                }
                                is Type.Song -> {
                                    val mediaPlayer = remember {
                                        MediaPlayer.create(
                                            context,
                                            ketPoint.type.res
                                        )
                                    }
                                    Box(modifier = Modifier
                                        .offset(
                                            ketPoint.offset.first.dp,
                                            ketPoint.offset.second.dp
                                        )
                                        .clickable {
                                            if (mediaPlayer.isPlaying) mediaPlayer.pause() else mediaPlayer.start()
                                        }) {
                                        val transition = rememberInfiniteTransition()
                                        val size = transition.animateFloat(
                                            initialValue = 10f,
                                            targetValue = 50f,
                                            animationSpec = infiniteRepeatable(
                                                animation = tween(2000),
                                                repeatMode = RepeatMode.Restart
                                            )
                                        )
                                        val color = transition.animateColor(
                                            initialValue = Color.White,
                                            targetValue = Color.Transparent,
                                            animationSpec = infiniteRepeatable(
                                                animation = tween(2000),
                                                repeatMode = RepeatMode.Restart
                                            )
                                        )
                                        Canvas(
                                            modifier = Modifier

                                        ) {
                                            drawCircle(
                                                color = Color.White, radius = 10f
                                            )
                                            drawCircle(
                                                color = Color.White,
                                                radius = 20f,
                                                style = Stroke(1f)
                                            )
                                            drawCircle(
                                                color = color.value, radius = size.value
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                    AnimatedVisibility(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(4.dp),
                        visible = !showKeyPoint,
                        exit = fadeOut(),
                        enter = fadeIn()
                    ) {
                        IconButton(onClick = { showKeyPoint = true }) {
                            val transition = rememberInfiniteTransition()
                            val size = transition.animateFloat(
                                initialValue = 10f,
                                targetValue = 40f,
                                animationSpec = infiniteRepeatable(
                                    animation = tween(2000),
                                    repeatMode = RepeatMode.Restart
                                )
                            )
                            val color = transition.animateColor(
                                initialValue = Color.White,
                                targetValue = Color.Transparent,
                                animationSpec = infiniteRepeatable(
                                    animation = tween(2000),
                                    repeatMode = RepeatMode.Restart
                                )
                            )
                            Icon(
                                modifier = Modifier
                                    .size(18.dp)
                                    .drawBehind {
                                        drawCircle(
                                            center = Offset(y = center.y - 9f, x = center.x - 1f),
                                            color = color.value,
                                            radius = size.value,
                                            style = Stroke(6f)
                                        )
                                    },
                                painter = painterResource(id = R.drawable.baseline_touch_app_24),
                                contentDescription = null,
                                tint = Color.White
                            )
                        }

                    }
                }
            }

            Divider(color = Color.White.copy(0.1f))
        }
    }
}