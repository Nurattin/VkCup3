package com.smartdev.vkcup2.ui.screens.reaction.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.smartdev.vkcup2.base.VkCupNavigationDestination
import com.smartdev.vkcup2.ui.screens.reaction.ReactionScreen

object ReactionDestination : VkCupNavigationDestination {
    override val route = "reaction_route"
}

fun NavGraphBuilder.reaction(
    onBackClick: () -> Unit,
) {
    composable(
        route = ReactionDestination.route,
    ) {
        ReactionScreen(onBackClick = onBackClick)
    }
}