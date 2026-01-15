package com.project.newsapp.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.newsapp.presentation.constants.CATEGORY


@Preview(showBackground = true)
@Composable
fun CustomTab(title: String="business") {
    val tabTitle = if(title== CATEGORY.TOP_HEADLINES.route)
        CATEGORY.TOP_HEADLINES.route
    else
        "${title[0].uppercase()}${title.substring(1)}"

    OutlinedCard(
        modifier = Modifier
            .wrapContentWidth()
            .height(55.dp)
            .padding(8.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 10.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = tabTitle,
                fontSize = 18.sp,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}
