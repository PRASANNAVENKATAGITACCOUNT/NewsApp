package com.project.newsapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun CustomInputField(placeholder : String="Enter",inputValue: String="", onChangeValue:()-> Unit={}) {
    val inputModifier = Modifier
        .fillMaxWidth()
        .padding(5.dp)
        .height(55.dp)
        .background(color = Color.Transparent)

    val textFieldColor = TextFieldDefaults.colors(
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent,
        focusedIndicatorColor = Color.Transparent,
    )

    OutlinedTextField(
        value = inputValue,
        onValueChange = { onChangeValue.invoke() },
        placeholder = { Text(placeholder) },
        modifier = inputModifier,
        colors = textFieldColor,
    )


}