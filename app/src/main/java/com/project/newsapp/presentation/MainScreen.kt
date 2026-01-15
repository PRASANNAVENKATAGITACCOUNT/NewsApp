package com.project.newsapp.presentation


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.project.newsapp.presentation.components.CustomTab
import com.project.newsapp.presentation.components.NewsAppNavHost
import com.project.newsapp.presentation.constants.CATEGORY

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    Scaffold { innerPadding->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            var searchText by remember {
                mutableStateOf("")
            }
            val navController = rememberNavController()
            var selectedDestination by rememberSaveable { mutableIntStateOf(0) }

            OutlinedTextField(
                value = searchText,
                onValueChange = {it-> searchText=it},
                modifier = Modifier.fillMaxWidth().padding(10.dp).height(55.dp),
                trailingIcon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = null,
                        modifier = Modifier.size(28.dp)
                    )
                },
                shape = RoundedCornerShape(20.dp),
                placeholder = {Text("Search....")}
            )


            ScrollableTabRow(
                selectedTabIndex = selectedDestination,
                edgePadding = 16.dp,
                divider = {},
                modifier = Modifier.fillMaxWidth()) {
                CATEGORY.getAllCategories().forEachIndexed { index, category->
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
            NewsAppNavHost(navController, CATEGORY.TOP_HEADLINES.route)
        }
    }
}