package com.smartdev.vkcup2.ui.screens.choose

import androidx.lifecycle.ViewModel
import com.smartdev.vkcup2.R
import kotlinx.coroutines.flow.MutableStateFlow


class InteractivePhotoViewModel : ViewModel() {
    val uiState = MutableStateFlow(InteractivePhotoUiState())
}


data class InteractivePhotoUiState(
    val article: List<Article> = mock,
    val page: Int = 0,
) {
    companion object {
        val mock = listOf(
            Article(
                title = "Путеводитель по Марсу. Все, что нужно знать о Красной планете людям," +
                        " не имеющим о ней никакого представления",
                image = listOf(
                    InteractiveImage(
                        src = R.drawable.mark,
                        keyPoint = listOf(
                            KeyPoint(
                                type = Type.Content(
                                    banner = R.drawable.olimp,
                                    title = "Потухший вулкан на Марсе, расположенный в провинции"
                                ),
                                offset = Pair(170, 80)
                            )
                        )
                    ),
                )
            ),
            Article(
                title = "Путеводитель по Марсу. Все, что нужно знать о Красной планете людям," +
                        " не имеющим о ней никакого представления",
                image = listOf(
                    InteractiveImage(
                        src = R.drawable.mark,
                        keyPoint = listOf(
                            KeyPoint(
                                type = Type.Content(
                                    banner = R.drawable.olimp,
                                    title = "Потухший вулкан на Марсе, расположенный в провинции"
                                ),
                                offset = Pair(170, 80)
                            )
                        )
                    ),
                )
            ),
            Article(
                title = "Путеводитель по Марсу. Все, что нужно знать о Красной планете людям," +
                        " не имеющим о ней никакого представления",
                image = listOf(
                    InteractiveImage(
                        src = R.drawable.mark,
                        keyPoint = listOf(
                            KeyPoint(
                                type = Type.Content(
                                    banner = R.drawable.olimp,
                                    title = "Потухший вулкан на Марсе, расположенный в провинции"
                                ),
                                offset = Pair(170, 80)
                            )
                        )
                    ),
                )
            ),
            Article(
                title = "Четвёртая по удалённости от Солнца и седьмая по размеру планета Солнечной" +
                        " системы; масса планеты составляет 10,7% массы Земли. Названа в честь Марса" +
                        " - древнеримского бога войны, соответствующего древнегреческому Аресу",
                image = listOf(
                    InteractiveImage(
                        src = R.drawable.mars2,
                        keyPoint = listOf(
                            KeyPoint(
                                type = Type.Song(
                                    res = R.raw.mars
                                ),
                                offset = Pair(300, 80)
                            ),
                            KeyPoint(
                                type = Type.Song(
                                    res = R.raw.earth
                                ),
                                offset = Pair(120, 80)
                            )
                        )
                    )
                )
            )
        )
    }
}

data class Article(
    val title: String,
    val image: List<InteractiveImage>
)

data class InteractiveImage(
    val src: Int,
    val keyPoint: List<KeyPoint>,
)

data class KeyPoint(
    val type: Type,
    val offset: Pair<Int, Int>
)

sealed interface Type {
    data class Content(
        val banner: Int,
        val title: String
    ) : Type

    data class Song(val res: Int) : Type
}