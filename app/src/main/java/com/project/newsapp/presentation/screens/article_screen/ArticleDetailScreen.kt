package com.project.newsapp.presentation.screens.article_screen


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.project.newsapp.domain.data.Article
import com.project.newsapp.presentation.components.ArticleTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun ArticleScreen(article: Article= Article(title="Article Title",
    urlToImage = null,
    content = "Content", description = "Description",
    author = "New Author", publishedAt = "15-01-2026"), onBackPress:()-> Unit={}) {

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp),
        topBar = {
            ArticleTopAppBar {
                onBackPress.invoke()
            }
        }
    ) {innerPadding->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            val textList = listOf<Pair<String?, Int>>(article.title to 25, article.author to 18, article.content to 18, article.description to 18)
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    article.urlToImage?.let { imageUrl->
                        AsyncImage(
                            model= imageUrl,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(15.dp)
                                .fillMaxHeight(0.5f)
                                .clip(RoundedCornerShape(30.dp)),
                            contentDescription = "News Image ",
                        )
                    }
                }
                item {
                    Row(modifier = Modifier.fillMaxWidth().height(45.dp)) {

                    }
                }
                items(textList.size){ index->
                    val textHeading = textList[index].first?:""
                    val fontSize = textList[index].second
                    ArticleTextContent(textHeading, textFontSize = fontSize)
                }
            }
        }
    }
}


@Composable
fun ArticleTextContent(contentText: String, textFontSize:Int =20, textColor: Color=Color.Black) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        fontSize = textFontSize.sp,
        text = contentText,
        color = textColor,
        maxLines = Int.MAX_VALUE,
        textAlign = TextAlign.Start,
        style = TextStyle(
            fontStyle = if(textFontSize == 20) FontStyle.Normal else FontStyle.Italic
        )
    )
}