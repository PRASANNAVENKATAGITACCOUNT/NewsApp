package com.project.newsapp.presentation.screens.home_screen


import android.content.Intent
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.carousel.CarouselItemScope
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.project.newsapp.R
import com.project.newsapp.domain.data.Article
import com.project.newsapp.presentation.screens.article_screen.ArticleScreenActivity
import com.project.newsapp.presentation.screens.search_news_screen.SearchNewsActivity
import com.project.newsapp.presentation.components.ArticleComponent
import com.project.newsapp.presentation.components.CustomTab
import com.project.newsapp.presentation.components.TitleComponent
import com.project.newsapp.presentation.constants.NEWSAPP_CATEGORY_PATH
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    var selectedDestination by rememberSaveable { mutableIntStateOf(0) }
    val context = LocalContext.current
    var topHeadlinesArticle by remember {
        mutableStateOf(emptyList<Article?>())
    }

    val viewModel = hiltViewModel<HomeViewModel>()
    topHeadlinesArticle =
        viewModel.topHeadLinesState.data?.articles?.filter { it -> it?.urlToImage != null }
            ?: emptyList()
    Log.d("MainScreen ", "List Of Articles $topHeadlinesArticle")

    val listState = rememberLazyListState()
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
        )
        {
            // Carousel Heading
            item {
                TitleComponent("Top Headlines ")
            }
            // Carousel
            item {
                if (topHeadlinesArticle.isNotEmpty()) {
                    TopNewsUICarousel(topHeadlinesArticle){
                        val intent = Intent(context, ArticleScreenActivity::class.java)
                        intent.putExtra("article", it)
                        context.startActivity(intent)
                    }
                }
            }

            // Sticky Header Category
            stickyHeader {
                ScrollableTabRow(
                    selectedTabIndex = selectedDestination,
                    edgePadding = 16.dp,
                    divider = {},
                    modifier = Modifier.fillMaxWidth()
                ) {
                    NEWSAPP_CATEGORY_PATH.getAllCategories().forEachIndexed { index, category ->
                        Tab(
                            selected = selectedDestination == index,
                            onClick = {
                                //navController.navigate(route = category.route)
                                //viewModel.fetchBasedOnCategory(category.route)
                                viewModel.selectCategory(category.route)
                                selectedDestination = index
                            },
                            text = {
                                CustomTab(category.route)
                            }
                        )
                    }
                }
            }

            // conditional rendering
            when {
                viewModel.newsState.loading -> {
                    item {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

                viewModel.newsState.error != null -> {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = viewModel.newsState.error ?: "Some Error Occured",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                viewModel.selectedCategory.isNotEmpty() -> {
                    val newsList = viewModel.getNewsBasedOnCategory()
                    Log.d("MainScreen : ", " $newsList ")
                    items(
                        items = newsList,
                    ) { article ->
                        article?.let {
                            ArticleComponent(article) { navigationArticle ->
                                val intent = Intent(context, ArticleScreenActivity::class.java)
                                intent.putExtra("article", navigationArticle)
                                context.startActivity(intent)
                            }
                        }
                    }
                }
            }

        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun TopNewsUICarousel(
    topHeadlinesArticle: List<Article?>,
    pagerState: PagerState = rememberPagerState(pageCount={topHeadlinesArticle.size}),
    navigateToArticle:(Article)-> Unit
) {
    HorizontalPager(state = pagerState) { page->
        CarouselItem(topHeadlinesArticle[page]){article ->
            navigateToArticle.invoke(article)
        }
    }
}

@Composable
private fun CarouselItem(item: Article?= Article(), onClick:(Article)-> Unit) {
    Box(
        modifier = Modifier
            .clickable{
                item?.let {
                    onClick.invoke(it)
                }
            }
            .padding(20.dp)
            .fillMaxWidth()
            .height(190.dp)
            .clip(RoundedCornerShape(18.dp))
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxSize(),
            model = item?.urlToImage ?: R.drawable.flower_image,
            contentDescription = "",
            contentScale = ContentScale.Crop
        )
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                item?.author ?: "",
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                ),
                modifier = Modifier.padding(5.dp)
            )
        }
    }
}



