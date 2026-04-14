package com.project.newsapp.presentation.screens.home_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun NewsStandScreenScreen() {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding->
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(innerPadding)
        ) {

        }
    }

}