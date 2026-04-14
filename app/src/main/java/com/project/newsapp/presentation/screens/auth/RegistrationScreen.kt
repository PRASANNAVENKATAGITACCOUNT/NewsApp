package com.project.newsapp.presentation.screens.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.project.newsapp.R
import com.project.newsapp.domain.data.UserData
import com.project.newsapp.presentation.components.InputFieldUI

@Preview(showBackground = true)
@Composable
fun RegistrationScreen(
    userEmail: String="",
    userPassword: String="",
    confirmUserPassword: String="",
    onEmailChange: (String)-> Unit={},
    onPasswordChange: (String)-> Unit={},
    onConfirmUserPasswordChange: (String)-> Unit={},
    onClickRegisterUser:()-> Unit={}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top=8.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Text(
            text = " Register ",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        InputFieldUI(
            textValue = userEmail,
            onValueChange = { email ->
                onEmailChange.invoke(email)
            },
            label = "Email"
        )

        InputFieldUI(
            textValue = userPassword,
            onValueChange = { password ->
                onPasswordChange.invoke(password)
            },
            leadingIcon = R.drawable.lock,
            forPassword = true,
            label = "Password"
        )

        InputFieldUI(
            textValue = confirmUserPassword,
            onValueChange = { password ->
                onConfirmUserPasswordChange.invoke(password)
            },
            leadingIcon = R.drawable.lock,
            forPassword = true,
            label = "Confirm Password"
        )

        Button(onClick = {
            onClickRegisterUser.invoke()
        }) {
            Text("Register")
        }

    }
}