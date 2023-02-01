package com.smartdev.vkcup2.ui.screens.interactive.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.smartdev.vkcup2.base.VkCupNavigationDestination
import com.smartdev.vkcup2.ui.screens.interactive.InteractivePhotoScreen

object InteractivePhotoDestination : VkCupNavigationDestination {
    override val route = "interactive_photo_route"
}

fun NavGraphBuilder.interactivePhoto(
    onBackClick: () -> Unit,
) {
    composable(
        route = InteractivePhotoDestination.route,
    ) {
        InteractivePhotoScreen(onBackClick = onBackClick)
    }
}