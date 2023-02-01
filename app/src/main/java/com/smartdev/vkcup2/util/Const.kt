package com.smartdev.vkcup2.util

import androidx.annotation.RawRes
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.Easing
import com.smartdev.vkcup2.R

object AnimateDuration {
    const val Fast = 150
    const val Long = 300
    const val ExtraLong = 500
}

object AnimateEasing {
    val EaseInBack: Easing = CubicBezierEasing(0.36f, 0f, 0.66f, -0.56f)
    val EaseOutBack: Easing = CubicBezierEasing(0.34f, 1.56f, 0.64f, 1f)
}

enum class Emoji(@RawRes val res: Int) {
    Emoji1(R.raw.imoji1),
    Emoji2(R.raw.imoji2),
    Emoji3(R.raw.imoji3),
    Emoji4(R.raw.imoji4),
    Emoji5(R.raw.imoji5),
    Emoji6(R.raw.imoji6),
    Emoji7(R.raw.imoji7),
    Emoji8(R.raw.imoji8),
    Emoji10(R.raw.imoji10),
    Emoji11(R.raw.imoji11),
    Emoji12(R.raw.imoji12),
    Emoji14(R.raw.imoji14),
    Emoji19(R.raw.emoji18),
}
