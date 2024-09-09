package com.challenge.dogbreeds.ui.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavType
import androidx.navigation.navArgument

/**
 * Class that manages the route name of destination and parameters to pass
 */
sealed class NavigationItem(val baseRoute: String) {
    open val route: String = baseRoute

    object List : NavigationItem(ScreenEnum.LIST.name)

    object Detail : NavigationItem(ScreenEnum.DETAIL.name){
        private const val paramId = "id"
        override val route: String = "$baseRoute/{$paramId}"
        val navArguments = listOf(
            navArgument(paramId) {
                type = NavType.StringType
            }
        )
        fun getBreedId(saveStateHandle: SavedStateHandle): String? = saveStateHandle.get<String>(paramId)
        fun buildBreedDetailsRoute(id: String) = "$baseRoute/${id}"
    }
}

enum class ScreenEnum() {
    LIST,
    DETAIL,
}