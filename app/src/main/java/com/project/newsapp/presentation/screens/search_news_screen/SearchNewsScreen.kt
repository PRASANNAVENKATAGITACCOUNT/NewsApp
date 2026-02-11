package com.project.newsapp.presentation.screens.search_news_screen



import android.content.Intent
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.project.newsapp.R
import com.project.newsapp.domain.data.Article
import com.project.newsapp.domain.data.NewsQueryState
import com.project.newsapp.presentation.screens.article_screen.ArticleScreenActivity
import com.project.newsapp.presentation.components.ArticleComponent

@Composable
fun SearchNewsScreen(
    newsQueryState: NewsQueryState,
    onBackPress:()-> Unit,
    isMicrophoneListening: Boolean=false,
    onSubmitText: (String) -> Unit,
    textFromSpeechRecognizer:()-> Unit? ={}
    ) {
    val context = LocalContext.current
    val searchViewModel = hiltViewModel<SearchNewsViewModel>()

    Scaffold(
        topBar = {
            SearchNewsTopAppBar(
                queryText = newsQueryState.newsQueryText,
                onQueryChange = {newQuery->
                    searchViewModel.updateQueryText(newQuery)
                },
                onBackPress = {
                    onBackPress()
                }, listenToMicrophone = {
                    textFromSpeechRecognizer()
                }) { queryText ->
                onSubmitText(queryText)
            }
        },
        modifier = Modifier.fillMaxSize(),
    ) {innerPadding->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Log.d("vgrjnngrjngjnr", "SearchNewsScreen: ${newsQueryState.newsQueryText}")
            when{
               isMicrophoneListening->{
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.mic),
                            contentDescription = "Content Description",
                            modifier = Modifier
                                .size(85.dp)
                                .clip(CircleShape),
                            tint = Color.Red
                        )
                    }
                }
                newsQueryState.error!=null->{
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text="${newsQueryState.error} ",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                newsQueryState.loading->{
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ){
                        CircularProgressIndicator()
                    }
                }
                newsQueryState.data!=null ->{
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        val listOfArticles : List<Article> = newsQueryState.data.articles?.filterNotNull()?:emptyList()
                        items(listOfArticles, key = {it-> it.hashCode()}){ article->
                            ArticleComponent(article){ selectedArticle->
                                val intent = Intent(context, ArticleScreenActivity::class.java)
                                intent.putExtra("article", selectedArticle)
                                context.startActivity(intent)
                            }
                        }
                    }
                }
            }

        }

    }

}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchNewsTopAppBar(queryText:String="",
                        onQueryChange:(String)-> Unit,
                        onBackPress:()-> Unit,
                        listenToMicrophone:()-> Unit,
                        onSubmitText:(String)-> Unit) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    TopAppBar(
        title = {
            OutlinedTextField(
                value = queryText,
                onValueChange = {it->  onQueryChange(it)},
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
                    .height(55.dp),
                trailingIcon = {
                    AnimatedVisibility(queryText.isEmpty()) {
                        IconButton(onClick = {
                            listenToMicrophone()
                        }) {
                            Icon(
                                painter = painterResource(R.drawable.mic),
                                contentDescription = null,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }
                    AnimatedVisibility(queryText.isNotEmpty()) {
                        IconButton(onClick = {
                           onQueryChange("")
                        }) {
                            Icon(
                                Icons.Default.Clear,
                                contentDescription = null,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }
                },
                textStyle = LocalTextStyle.current.copy(fontSize = 15.sp),
                shape = RoundedCornerShape(20.dp),
                placeholder = { Text("Search....") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = {
                    onSubmitText(queryText)
                }),
            )
        },
        navigationIcon = {
            AnimatedVisibility(
                queryText.isEmpty(),
                enter = slideInHorizontally(),
                exit = slideOutHorizontally()
            ) {
                IconButton(onClick = {
                    onBackPress()
                }) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        },
        modifier = Modifier.padding(top=8.dp, start = 0.dp)
    )

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        keyboardController?.show()
    }

}

@Preview(showBackground = true)
@Composable
fun SearchNewsScreenPreview(modifier: Modifier = Modifier) {
    SearchNewsScreen(
        newsQueryState = NewsQueryState(
            error = "No Network",
            loading = false,
            data = null
        ),
        onBackPress = {},
        onSubmitText = {}
    ){

    }
}