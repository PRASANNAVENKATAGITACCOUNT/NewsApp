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
import androidx.compose.runtime.remember
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


@Preview(showBackground = true)
@Composable
fun AuthScreen(
    navigateToMainScreen:()-> Unit={}
) {
    var userDataState by remember {
        mutableStateOf(UserData())
    }
    Scaffold(
        modifier= Modifier.fillMaxSize()
    ){ innerPadding->

        ConstraintLayout(
            modifier = Modifier.fillMaxSize().padding(innerPadding)
        )
        {
            val (elevatedCard)  = createRefs()

            ElevatedCard(
                modifier = Modifier.padding(18.dp).constrainAs(elevatedCard){
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }.aspectRatio(25f/35f) //width to height ratio

            ) {
                Column(modifier = Modifier.fillMaxSize().padding(top = 20.dp)) {
                    LoginScreen(
                        userEmail = userDataState.userEmail,
                        userPassword = userDataState.password,
                        onEmailChange = { enteredEmail->
                            userDataState = userDataState.copy(userEmail = enteredEmail)
                        },
                        onPasswordChange = { enteredPassword->
                            userDataState = userDataState.copy(password = enteredPassword)
                        },
                        onClickSignUp = {

                        },
                        onClickLoginButton = {
                            navigateToMainScreen.invoke()
                        }
                    )
                }
            }

        }
    }
}

