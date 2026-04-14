package com.project.newsapp.presentation.screens.main

sealed class NewsScreen(val route: String) {
    object HomeScreen : NewsScreen("home_screen")
    object FollowingScreen : NewsScreen("following_screen")
    object NewsStandScreen : NewsScreen("news_stand_screen")
}