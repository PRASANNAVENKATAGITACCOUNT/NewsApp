package com.project.newsapp.presentation.screens


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    finishSplashScreen:()-> Unit
) {
    LaunchedEffect(Unit) {
        delay(3500)
        finishSplashScreen()
    }



    var showNewsAppText by remember {
        mutableStateOf(true)
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        AnimatedVisibility(
            showNewsAppText,
            enter = slideInHorizontally()
        ) {
            Text(
                text = "NEWS APP",
                fontWeight = FontWeight.Bold,
                style = TextStyle(
                    fontSize = 30.sp,
                    color = Color.Red
                )
            )
        }

    }


}

