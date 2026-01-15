package com.project.newsapp.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.project.newsapp.presentation.constants.CATEGORY
import com.project.newsapp.presentation.MainViewModel
import com.project.newsapp.presentation.category.CategoryScreen

@Composable
fun NewsAppNavHost(navController: NavHostController, startDestination: String) {
    NavHost(navController, startDestination = startDestination) {
        composable(CATEGORY.TOP_HEADLINES.route) {
            val viewModel = hiltViewModel<MainViewModel>()
            LaunchedEffect(CATEGORY.TOP_HEADLINES.route) {
                viewModel.fetchData()
            }
            CategoryScreen(
                state=viewModel.state
            )
        }
        composable(CATEGORY.BUSINESS.route) {
            val viewModel = hiltViewModel<MainViewModel>()
            LaunchedEffect(CATEGORY.BUSINESS.route) {
                viewModel.fetchBasedOnCategory(CATEGORY.BUSINESS.route)
            }
            CategoryScreen(
                state=viewModel.state
            )
        }
        composable(CATEGORY.ENTERTAINMENT.route) {
            val viewModel = hiltViewModel<MainViewModel>()
            LaunchedEffect(CATEGORY.ENTERTAINMENT.route) {
                viewModel.fetchBasedOnCategory(CATEGORY.ENTERTAINMENT.route)
            }
            CategoryScreen(
                state=viewModel.state
            )
        }
        composable(CATEGORY.GENERAL.route) {
            val viewModel = hiltViewModel<MainViewModel>()
            LaunchedEffect(CATEGORY.GENERAL.route) {
                viewModel.fetchBasedOnCategory(CATEGORY.GENERAL.route)
            }
            CategoryScreen(
                state=viewModel.state
            )
        }
        composable(CATEGORY.HEALTH.route) {
            val viewModel = hiltViewModel<MainViewModel>()
            LaunchedEffect(CATEGORY.HEALTH.route) {
                viewModel.fetchBasedOnCategory(CATEGORY.HEALTH.route)
            }
            CategoryScreen(
                state=viewModel.state
            )
        }
        composable(CATEGORY.SCIENCE.route) {
            val viewModel = hiltViewModel<MainViewModel>()
            LaunchedEffect(CATEGORY.SCIENCE.route) {
                viewModel.fetchBasedOnCategory(CATEGORY.SCIENCE.route)
            }
            CategoryScreen(
                state= viewModel.state
            )
        }
        composable(CATEGORY.TECHNOLOGY.route) {
            val viewModel = hiltViewModel<MainViewModel>()
            LaunchedEffect(CATEGORY.TECHNOLOGY.route) {
                viewModel.fetchBasedOnCategory(CATEGORY.TECHNOLOGY.route)
            }
            CategoryScreen(
                state= viewModel.state
            )
        }
    }
}

