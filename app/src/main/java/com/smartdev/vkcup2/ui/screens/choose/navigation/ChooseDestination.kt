package com.smartdev.vkcup2.ui.screens.choose.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.smartdev.vkcup2.base.VkCupNavigationDestination
import com.smartdev.vkcup2.ui.screens.choose.InteractivePhotoScreen
import com.smartdev.vkcup2.ui.screens.reaction.ReactionScreen

object ChooseDestination : VkCupNavigationDestination {
    override val route = "choose_route"
}

fun NavGraphBuilder.choose(
    navigateTo: (route: String) -> Unit
) {
    composable(
        route = ChooseDestination.route,
    ) {
        ReactionScreen(
        )
    }
}