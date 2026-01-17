package com.project.newsapp.presentation.screens


import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.project.newsapp.presentation.SearchNewsActivity
import com.project.newsapp.presentation.components.CustomTab
import com.project.newsapp.presentation.components.NewsAppNavHost
import com.project.newsapp.presentation.constants.NEWSAPP_CATEGORY_PATH

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    var selectedDestination by rememberSaveable { mutableIntStateOf(0) }
    val context = LocalContext.current
    Scaffold(
        topBar = {
            NewsTopAppBar {
                val intent = Intent(context, SearchNewsActivity::class.java)
                context.startActivity(intent)
            }
        }
    ) { innerPadding->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            ScrollableTabRow(
                selectedTabIndex = selectedDestination,
                edgePadding = 16.dp,
                divider = {},
                modifier = Modifier.fillMaxWidth()) {
                NEWSAPP_CATEGORY_PATH.getAllCategories().forEachIndexed { index, category->
                    Tab(
                        selected= selectedDestination == index,
                        onClick = {
                            navController.navigate(route = category.route)
                            selectedDestination = index
                        },
                        text = {
                            CustomTab(category.route)
                        }
                    )
                }
            }
            NewsAppNavHost(navController, NEWSAPP_CATEGORY_PATH.TOP_HEADLINES.route)
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