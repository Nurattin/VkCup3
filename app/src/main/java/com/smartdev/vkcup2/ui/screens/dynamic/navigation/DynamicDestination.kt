package com.smartdev.vkcup2.ui.screens.dynamic.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.smartdev.vkcup2.base.VkCupNavigationDestination
import com.smartdev.vkcup2.ui.screens.dynamic.DynamicScreen

object DynamicDestination : VkCupNavigationDestination {
    override val route = "dynamic_route"
}

fun NavGraphBuilder.dynamic(
    onBackClick: () -> Unit,
) {
    composable(
        route = DynamicDestination.route,
    ) {
        DynamicScreen()
    }
}