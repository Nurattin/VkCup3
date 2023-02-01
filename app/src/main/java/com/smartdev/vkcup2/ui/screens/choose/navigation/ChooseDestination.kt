package com.smartdev.vkcup2.ui.screens.choose.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.smartdev.vkcup2.R
import com.smartdev.vkcup2.base.VkCupNavigationDestination
import com.smartdev.vkcup2.ui.screens.choose.ChooseScreen
import com.smartdev.vkcup2.ui.screens.dynamic.navigation.DynamicDestination
import com.smartdev.vkcup2.ui.screens.interactive.navigation.InteractivePhotoDestination
import com.smartdev.vkcup2.ui.screens.reaction.navigation.ReactionDestination

object ChooseDestination : VkCupNavigationDestination {
    override val route = "choose_route"
}

fun NavGraphBuilder.choose(
    navigateTo: (route: String) -> Unit
) {
    composable(
        route = ChooseDestination.route,
    ) {
        ChooseScreen(onClickChooseBtn = {
            navigateTo(
                when (it) {
                    R.string.interactive_image -> InteractivePhotoDestination.route
                    R.string.publication_reaction -> ReactionDestination.route
                    R.string.dynamic -> DynamicDestination.route
                    else -> throw IllegalStateException("Not found route")
                }
            )
        })
    }
}