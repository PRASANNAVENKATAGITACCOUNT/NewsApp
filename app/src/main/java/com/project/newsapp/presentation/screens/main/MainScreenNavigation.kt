package com.project.newsapp.presentation.screens.main

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.project.newsapp.presentation.screens.auth.AuthScreen
import com.project.newsapp.presentation.screens.home_screen.FollowingScreen
import com.project.newsapp.presentation.screens.home_screen.HomeScreen
import com.project.newsapp.presentation.screens.home_screen.NewsStandScreenScreen

@Composable
fun MainScreenNav(navController: NavHostController, modifier: Modifier= Modifier) {
    NavHost(navController = navController, startDestination = NewsScreen.HomeScreen.route,
        modifier = modifier) {

        composable(route = NewsScreen.HomeScreen.route) {
            HomeScreen()
        }
        composable(route = NewsScreen.FollowingScreen.route) {
            FollowingScreen()
        }
        composable(route = NewsScreen.NewsStandScreen.route) {
            NewsStandScreenScreen()
        }

    }
}