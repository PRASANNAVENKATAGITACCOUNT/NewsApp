package com.project.newsapp.presentation.components

import android.R
import androidx.annotation.DrawableRes
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun CustomIcon(@DrawableRes icon: Int, onClick:()-> Unit={}) {
    IconButton(
        onClick={
            onClick.invoke()
        },
        modifier = Modifier
            .padding(8.dp)
            .border(1.dp, shape = CircleShape, color = Color.LightGray)
            .size(55.dp).clip(CircleShape)
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = ""
        )
    }
}