package com.project.newsapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.project.newsapp.domain.data.Article
import java.nio.file.WatchEvent

@Preview(showBackground = true)
@Composable
fun ArticleComponent(article: Article= Article(title="Article Title",
    description = "Description", author = "New Author", publishedAt = "15-01-2026"), navigate:(Article)-> Unit={}) {
    val textColor = Color.Black  // if(article.urlToImage!=null) Color.White else Color.Black

    ElevatedCard(
        modifier = Modifier
            .clickable{
                navigate(article)
            }
            .fillMaxWidth()
            .fillMaxHeight(0.25f)
            .padding(10.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 10.dp),
//        colors = CardDefaults.elevatedCardColors(
//            containerColor = Color.White
//        )
    ) {
        Row (
            modifier = Modifier.fillMaxSize() .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            article.urlToImage?.let {
                AsyncImage(
                    model = it,
                    contentDescription = "News Image ",
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .weight(0.5f)
                        .padding(5.dp)
                )
            }
            Column(
                modifier = Modifier.weight(1f) .padding(5.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = article.title ?: "",
                    fontSize = 14.sp,
                    color = textColor,
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(
                        textAlign = TextAlign.Start,
                    ),
                    maxLines = 5,
                    modifier = Modifier.padding(5.dp)
                )
                Text(
                    text = article.author ?: "",
                    color = textColor,
                    style = TextStyle(
                        textAlign = TextAlign.Start,
                    ),
                    fontSize = 8.sp,
                    modifier = Modifier.padding(5.dp)
                )
                Text(
                    text = article.publishedAt ?: "",
                    color = textColor,
                    style = TextStyle(
                        textAlign = TextAlign.Start,
                    ),
                    fontSize = 8.sp,
                    modifier = Modifier.padding(5.dp)
                )
            }

        }
    }
}