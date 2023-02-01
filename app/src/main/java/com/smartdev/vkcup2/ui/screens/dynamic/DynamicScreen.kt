package com.smartdev.vkcup2.ui.screens.dynamic

import android.net.Uri
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.animation.*
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.Player.STATE_ENDED
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.smartdev.vkcup2.R
import com.smartdev.vkcup2.ui.theme.MainBackground
import com.smartdev.vkcup2.util.Emoji
import com.smartdev.vkcup2.util.vibrateDevice
import kotlin.math.*


@Composable
fun DynamicScreen(modifier: Modifier = Modifier) {
    val images = remember {
        listOf(
            R.drawable.photo1,
            R.drawable.photo2,
            R.drawable.photo3,
            R.drawable.photo4,
        )
    }
    val texts = remember {
        listOf(
            "Delegate in Kotlin",
            "Lazy grid in Jetpack Compose",
            "KotlinKenya Newsletter #7",
            "Extension Functions & Inline Functions in Kotlin",
            "Video and Image Capture with CameraX in Android",
            "Functional Programming in Kotlin with ArrowKt: Understanding the Option Data Type"
        )
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .background(Color.Black),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        repeat(2) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(0.8f)
                    .clip(MaterialTheme.shapes.large)
                    .background(MainBackground)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier
                            .size(12.dp)
                            .padding(),
                        painter = painterResource(id = R.drawable.dzen),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        text = "Рекомендации специально для вас",
                        color = Color.White.copy(0.2f),
                        style = MaterialTheme.typography.subtitle2
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .horizontalScroll(rememberScrollState())
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(45.dp)

                ) {
                    repeat(2) {
                        Column(
                            Modifier.fillMaxHeight(),
                            verticalArrangement = Arrangement.SpaceAround
                        ) {
                            repeat(2) {
                                DynamicImage(
                                    modifier = Modifier,
                                    image = remember { images.random() },
                                    rate = (4..5).random() / 10f,
                                    text = remember { texts.random() }
                                )
                            }
                        }
                        Column(
                            Modifier
                                .fillMaxHeight(),
                            verticalArrangement = Arrangement.Center
                        ) {

                            VideoPlayer(rate = (7..9).random() / 10f)
                        }
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
@Composable
fun DynamicImage(
    modifier: Modifier = Modifier,
    image: Int,
    rate: Float,
    text: String,
) {
    val context = LocalContext.current
    val randomOffsetX = remember { (-20..20).random() }
    val randomOffsetY = remember { (-20..20).random() }
    val interactionSource = remember { MutableInteractionSource() }
    var isExpanded by remember { mutableStateOf(false) }
    val sizeV by animateDpAsState(targetValue = if (isExpanded) (350 * rate).dp else (300 * rate).dp)
    Column(
        modifier = modifier
            .offset(randomOffsetX.dp, randomOffsetY.dp)
            .width(sizeV)
            .combinedClickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = { if (isExpanded) isExpanded = false },
                onLongClick = {
                    isExpanded = !isExpanded
                    vibrateDevice(context)
                }
            ),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier) {
            var repeatAnim by remember { mutableStateOf(1) }
            LaunchedEffect(key1 = isExpanded) {
                if (isExpanded) {
                    repeatAnim = if (repeatAnim == 1) 2 else 1
                }
            }

            val emoji = remember { Emoji.values().random() }
            val composition =
                rememberLottieComposition(spec = LottieCompositionSpec.RawRes(emoji.res))
            Image(
                modifier = Modifier
                    .align(Alignment.Center)
                    .clip(CircleShape)
                    .size(sizeV)
                    .fillMaxWidth()
                    .aspectRatio(1f),
                painter = painterResource(id = image),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            LottieAnimation(
                modifier = Modifier
                    .fillMaxWidth(0.2f)
                    .aspectRatio(1f)
                    .align(Alignment.TopEnd)
                    .clip(CircleShape)
                    .background(MainBackground),
                composition = composition.value,
                iterations = repeatAnim
            )
        }
        AnimatedContent(targetState = isExpanded) {
            Text(
                text = text,
                style = MaterialTheme.typography.subtitle2,
                color = Color.White,
                textAlign = TextAlign.Center,
                maxLines = if (it) 4 else 2,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun VideoPlayer(
    modifier: Modifier = Modifier, rate: Float,
) {

    val context = LocalContext.current
    val videoURL =
        "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/TearsOfSteel.mp4"
    var isExpanded by remember { mutableStateOf(false) }
    val randomOffsetX = remember { (-20..20).random() }
    val randomOffsetY = remember { (-20..20).random() }
    val sizeV by animateDpAsState(targetValue = if (isExpanded) (350 * rate).dp else (300 * rate).dp)

    val exoPlayer = remember {
        com.google.android.exoplayer2.ExoPlayer.Builder(context).build().apply {
            val mediaItem = com.google.android.exoplayer2.MediaItem.Builder()
                .setUri(Uri.parse(videoURL))
                .build()
            setMediaItem(mediaItem)
            prepare()
            playWhenReady = true
            pause()
        }
    }

    var isPlaying by remember { mutableStateOf(exoPlayer.isPlaying) }
    var totalDuration by remember { mutableStateOf(0L) }
    var currentTime by remember { mutableStateOf(0L) }
    var bufferedPercentage by remember { mutableStateOf(0) }
    var playbackState by remember { mutableStateOf(exoPlayer.playbackState) }


    Column(
        modifier = modifier
            .offset(randomOffsetX.dp, randomOffsetY.dp)
            .width(sizeV),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        DisposableEffect(key1 = Unit) {
            val listener = object : Player.Listener {
                override fun onEvents(
                    player: Player,
                    events: Player.Events
                ) {
                    super.onEvents(player, events)
                    totalDuration = player.duration.coerceAtLeast(0L)
                    currentTime = player.currentPosition.coerceAtLeast(0L)
                    bufferedPercentage = player.bufferedPercentage
                    isPlaying = player.isPlaying
                    playbackState = player.playbackState
                }
            }

            exoPlayer.addListener(listener)

            onDispose {
                exoPlayer.removeListener(listener)
                exoPlayer.release()
            }
        }


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(CircleShape)
        ) {
            AndroidView(
                modifier = Modifier
                    .fillMaxSize()
                    .combinedClickable(
                        onClick = {},
                        onLongClick = {
                            when {
                                exoPlayer.isPlaying -> {
                                    isExpanded = false
                                    exoPlayer.pause()
                                }
                                exoPlayer.isPlaying.not() && playbackState == STATE_ENDED && isExpanded -> {
                                    exoPlayer.seekTo(0)
                                    exoPlayer.playWhenReady = true
                                }
                                else -> {
                                    isExpanded = true
                                    exoPlayer.play()
                                }
                            }
                            isPlaying = isPlaying.not()
                        }
                    ),
                factory = {
                    StyledPlayerView(context).apply {
                        resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                        player = exoPlayer
                        useController = false
                        layoutParams =
                            FrameLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT
                            )
                        defaultArtwork = context.getDrawable(R.drawable.dzen)
                    }
                }
            )
            androidx.compose.animation.AnimatedVisibility(
                visible = isExpanded,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                val progress =
                    if (currentTime.toFloat() == 0f || totalDuration.toFloat() == 0f) 0f
                    else currentTime.toFloat() / totalDuration.toFloat()

                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                        .zIndex(1f)
                ) {
                    drawArc(
                        color = Color.White,
                        startAngle = -90f,
                        sweepAngle = 360 * progress,
                        useCenter = false,
                        style = Stroke(30f),
                    )

                    drawCircle(
                        color = Color.White.copy(0.3f),
                        style = Stroke(30f),
                        radius = size.width / 2f
                    )
                }
                CenterControls(isPlaying = { isPlaying }, onPauseToggle = {
                    when {
                        exoPlayer.isPlaying -> exoPlayer.pause()
                        exoPlayer.isPlaying.not() &&
                                playbackState == STATE_ENDED -> {
                            exoPlayer.seekTo(0)
                            exoPlayer.playWhenReady = true
                        }
                        else -> {
                            // play the video
                            // it's already paused
                            exoPlayer.play()
                        }
                    }
                    isPlaying = isPlaying.not()
                })
            }
        }
        Text(
            text = "Tears of Steel",
            style = MaterialTheme.typography.subtitle2,
            color = Color.White,
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun CenterControls(
    modifier: Modifier = Modifier,
    isPlaying: () -> Boolean,
    onPauseToggle: () -> Unit,
) {
    val isVideoPlaying = remember(isPlaying()) { isPlaying() }

    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        IconButton(modifier = Modifier.size(40.dp), onClick = onPauseToggle) {
            Icon(
                modifier = Modifier.fillMaxSize(),
                painter =
                when {
                    isVideoPlaying -> {
                        painterResource(id = R.drawable.round_pause_24)
                    }
                    else -> {
                        painterResource(id = R.drawable.round_play_arrow_24)
                    }
                },
                tint = Color.White,
                contentDescription = "Play/Pause"
            )
        }
    }
}
