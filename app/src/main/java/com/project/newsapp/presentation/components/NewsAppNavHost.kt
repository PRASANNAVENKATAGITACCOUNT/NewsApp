package com.project.newsapp.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.project.newsapp.presentation.constants.NEWSAPP_CATEGORY_PATH
import com.project.newsapp.presentation.MainViewModel
import com.project.newsapp.presentation.screens.CategoryScreen

@Composable
fun NewsAppNavHost(navController: NavHostController, startDestination: String) {
    NavHost(navController, startDestination = startDestination) {
        composable(NEWSAPP_CATEGORY_PATH.TOP_HEADLINES.route) {
            val viewModel = hiltViewModel<MainViewModel>()
            LaunchedEffect(NEWSAPP_CATEGORY_PATH.TOP_HEADLINES.route) {
                viewModel.fetchData()
            }
            CategoryScreen(
                state=viewModel.state
            )
        }
        composable(NEWSAPP_CATEGORY_PATH.BUSINESS.route) {
            val viewModel = hiltViewModel<MainViewModel>()
            LaunchedEffect(NEWSAPP_CATEGORY_PATH.BUSINESS.route) {
                viewModel.fetchBasedOnCategory(NEWSAPP_CATEGORY_PATH.BUSINESS.route)
            }
            CategoryScreen(
                state=viewModel.state
            )
        }
        composable(NEWSAPP_CATEGORY_PATH.ENTERTAINMENT.route) {
            val viewModel = hiltViewModel<MainViewModel>()
            LaunchedEffect(NEWSAPP_CATEGORY_PATH.ENTERTAINMENT.route) {
                viewModel.fetchBasedOnCategory(NEWSAPP_CATEGORY_PATH.ENTERTAINMENT.route)
            }
            CategoryScreen(
                state=viewModel.state
            )
        }
        composable(NEWSAPP_CATEGORY_PATH.GENERAL.route) {
            val viewModel = hiltViewModel<MainViewModel>()
            LaunchedEffect(NEWSAPP_CATEGORY_PATH.GENERAL.route) {
                viewModel.fetchBasedOnCategory(NEWSAPP_CATEGORY_PATH.GENERAL.route)
            }
            CategoryScreen(
                state=viewModel.state
            )
        }
        composable(NEWSAPP_CATEGORY_PATH.HEALTH.route) {
            val viewModel = hiltViewModel<MainViewModel>()
            LaunchedEffect(NEWSAPP_CATEGORY_PATH.HEALTH.route) {
                viewModel.fetchBasedOnCategory(NEWSAPP_CATEGORY_PATH.HEALTH.route)
            }
            CategoryScreen(
                state=viewModel.state
            )
        }
        composable(NEWSAPP_CATEGORY_PATH.SCIENCE.route) {
            val viewModel = hiltViewModel<MainViewModel>()
            LaunchedEffect(NEWSAPP_CATEGORY_PATH.SCIENCE.route) {
                viewModel.fetchBasedOnCategory(NEWSAPP_CATEGORY_PATH.SCIENCE.route)
            }
            CategoryScreen(
                state= viewModel.state
            )
        }
        composable(NEWSAPP_CATEGORY_PATH.TECHNOLOGY.route) {
            val viewModel = hiltViewModel<MainViewModel>()
            LaunchedEffect(NEWSAPP_CATEGORY_PATH.TECHNOLOGY.route) {
                viewModel.fetchBasedOnCategory(NEWSAPP_CATEGORY_PATH.TECHNOLOGY.route)
            }
            CategoryScreen(
                state= viewModel.state
            )
        }
    }
}

