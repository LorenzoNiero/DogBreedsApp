package com.challenge.dogbreeds.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.challenge.digbreeds.list.presentation.ListScreen
import com.challenge.dogbreeds.ui.navigation.NavigationItem

/**
 * Component that manages navigation through pages at the change of route
 * @param navController navigation manager
 */
@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String = NavigationItem.List.route,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavigationItem.List.route) {
            ListScreen(navController)
        }
        composable(NavigationItem.Detail.route,
            arguments = NavigationItem.Detail.navArguments
            ) {
            //todo added screen detail
//            DetailScreen(navController)
        }
    }
}

