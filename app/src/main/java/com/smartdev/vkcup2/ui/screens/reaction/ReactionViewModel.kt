package com.smartdev.vkcup2.ui.screens.reaction

import androidx.lifecycle.ViewModel
import com.smartdev.vkcup2.R
import com.smartdev.vkcup2.util.Emoji
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class ReactionViewModel : ViewModel() {
    val uiState = MutableStateFlow(ReactionUiState())


    fun reactPublication(publicationPos: Int, reaction: Emoji) {
        uiState.update { currentState ->
            var selectedReaction: Reaction? = null
            val updateArticle = currentState.article.toMutableList().let {
                var updateReaction =
                    if (it[publicationPos].reaction.any { react -> react.emoji == reaction }) {
                        it[publicationPos].reaction
                    } else {
                        it[publicationPos].reaction.plus(Reaction(emoji = reaction, count = 0))
                    }
                updateReaction = updateReaction.map { react ->
                    if (reaction == react.emoji) {
                        if (react.isSelected) {
                            react.copy(isSelected = false, count = react.count - 1)
                        } else {
                            selectedReaction = react
                            react.copy(isSelected = true, count = react.count + 1)
                        }
                    } else if (react.isSelected) react.copy(
                        isSelected = false,
                        count = react.count - 1
                    )
                    else react
                }.filter { react -> react.count != 0 }

                it[publicationPos] = it[publicationPos].copy(
                    reaction = updateReaction,
                    selectedReaction = selectedReaction
                )
                it.toList()
            }
            currentState.copy(article = updateArticle)
        }
    }
}

data class ReactionUiState(
    val article: List<ReactionArticle> = mock,
    val page: Int = 0,
) {
    companion object {
        val mock = listOf(
            ReactionArticle(
                title = "Тенденции развития Android на 2022 год",
                image = listOf(R.drawable.reaction1),
                subtitle = "Тенденции развития Android на 2022 год\n" +
                        "Jetpack Compose, Hilt, Kotlin Flow и сопрограммы" +
                        " — обязательные навыки для разработчиков Android" +
                        " - Один из наших главных приоритетов как мобильных" +
                        " разработчиков — оставаться в курсе событий и проверять" +
                        " последние анонсы, даже если это означает выход из нашей" +
                        " зоны комфорта. В прошлом году в мире Android произошло" +
                        " несколько интересных событий, однако я сосредоточусь на" +
                        " наиболее важных из них, которые...",
                reaction = listOf(
                    Reaction(emoji = Emoji.Emoji14, count = 245, isSelected = false),
                    Reaction(emoji = Emoji.Emoji4, count = 112, isSelected = false),
                    Reaction(emoji = Emoji.Emoji6, count = 23, isSelected = false),
                )
            ),
            ReactionArticle(
                title = "Исследование библиотек управления зависимостями для мультиплатформенных" +
                        " мобильных устройств Kotlin",
                subtitle = "Kotlin Multiplatform Mobile (или просто Мультиплатформенный мобильный)" +
                        " - это новый SDK от JetBrains, который позволяет разработчикам iOS и" +
                        " Android обмениваться кодом между двумя платформами. Общий код написан" +
                        " на Kotlin. Типичные варианты использования включают: совместное использование" +
                        " сетей, хранение данных, внутренние библиотеки и алгоритмы, но выбор за вами!...",
                image = listOf(R.drawable.reaction3),
                reaction = listOf(
                    Reaction(emoji = Emoji.Emoji14, count = 545, isSelected = false),
                    Reaction(emoji = Emoji.Emoji19, count = 145, isSelected = false),
                )
            ),
            ReactionArticle(
                title = "Введение к анимации с помощью Jetpack Compose",
                subtitle = "Canvas transformations and transitions",
                image = listOf(R.drawable.reaction2),
                reaction = listOf(
                    Reaction(emoji = Emoji.Emoji14, count = 95, isSelected = false),
                    Reaction(emoji = Emoji.Emoji12, count = 13, isSelected = false),
                )
            )
        )
    }
}

data class ReactionArticle(
    val title: String,
    val subtitle: String,
    val image: List<Int>,
    val selectedReaction: Reaction? = null,
    val reaction: List<Reaction>
)

data class Reaction(
    val emoji: Emoji,
    val count: Int,
    val isSelected: Boolean = false
)
