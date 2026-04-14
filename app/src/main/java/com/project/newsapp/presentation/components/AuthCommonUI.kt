package com.project.newsapp.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.newsapp.R

@Composable
fun InputFieldUI(
    placeholder: String="Enter",
    textValue: String="",
    onValueChange:(String)-> Unit={},
    leadingIcon: Int = R.drawable.email,
    forPassword: Boolean = false,
    label: String ="Email"
) {

    var visibilityIcon by remember {
        mutableStateOf(R.drawable.hide)
    }
    var passwordTextVisibility by remember {
        mutableStateOf(
            PasswordVisualData(
                visualTransformation = if(forPassword) PasswordVisualTransformation() else VisualTransformation.None,
                keyboardOptions = if(forPassword) {
                    KeyboardOptions(keyboardType = KeyboardType.Password)
                }else {
                    KeyboardOptions(keyboardType = KeyboardType.Text)
                },
            )
        )
    }

    OutlinedTextField(
        value = textValue,
        onValueChange = { onValueChange(it) },
        placeholder = { Text(placeholder) },
        modifier = Modifier
            .padding(5.dp)
            .height(65.dp)
            .fillMaxWidth(0.9f),
        textStyle = TextStyle(
            fontSize = 18.sp
        ),
        maxLines = 1,
        label = {Text(text= label )},
        leadingIcon = {
            Icon(painter = painterResource(leadingIcon), contentDescription = "")
        },
        visualTransformation = passwordTextVisibility.visualTransformation,
        keyboardOptions = passwordTextVisibility.keyboardOptions,
        trailingIcon={
            if(forPassword){
                IconButton(
                    onClick = {
                        visibilityIcon = if(visibilityIcon == R.drawable.hide) {
                            passwordTextVisibility = passwordTextVisibility.copy(
                                visualTransformation = VisualTransformation.None,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                            )
                            R.drawable.visible
                        } else{
                            passwordTextVisibility = passwordTextVisibility.copy(
                                visualTransformation = PasswordVisualTransformation(),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                            )
                            R.drawable.hide
                        }
                    },
                    modifier = Modifier.padding(20.dp)
                ) {
                    Icon(painter = painterResource(visibilityIcon), contentDescription = "")
                }
            }
        }
    )
}

data class PasswordVisualData(
    val visualTransformation : VisualTransformation = VisualTransformation.None,
    val keyboardOptions : KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
)
