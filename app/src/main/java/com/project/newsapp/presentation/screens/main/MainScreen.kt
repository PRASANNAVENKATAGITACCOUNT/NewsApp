package com.project.newsapp.presentation.screens.main

import com.project.newsapp.R
import android.content.Intent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.project.newsapp.presentation.screens.search_news_screen.SearchNewsActivity

@Preview(showBackground = true)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val context = LocalContext.current
    Scaffold(
        modifier = Modifier,
        topBar = {
            NewsTopAppBar {
                val intent = Intent(context, SearchNewsActivity::class.java)
                context.startActivity(intent)
            }
        },
        bottomBar = {
            NewsBottomNav(){ route->
                navController.navigate(route=route)
            }
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxWidth().padding(paddingValues)
        ) {
            MainScreenNav(navController, modifier = Modifier)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsTopAppBar(onClickOfSearch:()-> Unit) {
    TopAppBar(
        title = {Text("NEWS APP")},
        modifier = Modifier,
        actions = {
            IconButton(onClick = {
                onClickOfSearch()
            }) {
                Icon(Icons.Default.Search, contentDescription = "Search Icon")
            }

        },
    )
}


@Composable
fun NewsBottomNav(onClickItem:(String)-> Unit) {
    val navList = mapOf<String, String>(
        "Home" to NewsScreen.HomeScreen.route,
        "Following" to NewsScreen.FollowingScreen.route,
        "Newsstand" to NewsScreen.NewsStandScreen.route)
    var selectedTitle by remember { mutableStateOf( "Home") }
    NavigationBar(
        modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars)
    ) {
        navList.forEach { (title, route) ->
            NavigationBarItem(
                selected = selectedTitle == title,
                onClick = {
                    selectedTitle = title
                    onClickItem.invoke(route)
                },
                icon = {  Icon(painter = painterResource(getIcon(route), ), contentDescription = "") },
                label = { Text(text = title) }
            )
        }
    }
}

fun getIcon(title: String) :   Int  {
    val icon= when(title){
        NewsScreen.HomeScreen.route -> return R.drawable.home_icon
        NewsScreen.FollowingScreen.route -> return R.drawable.following_icon
        NewsScreen.NewsStandScreen.route -> return R.drawable.newsstand_icon
        else -> R.drawable.flower_image
    }
    return  icon
}

