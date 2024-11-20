package org.example.pointofsalesystem.ui.screens.login

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.pointofsalesystem.ui.components.buttons.ButtonGoogle
import org.example.pointofsalesystem.ui.components.buttons.ButtonLink
import org.example.pointofsalesystem.ui.components.buttons.ButtonPrimary
import org.example.pointofsalesystem.ui.components.forms.CheckboxField
import org.example.pointofsalesystem.ui.components.forms.Field
import org.example.pointofsalesystem.ui.components.forms.FieldPassword
import org.example.pointofsalesystem.ui.components.text.*
import org.example.pointofsalesystem.ui.components.widgets.Logo
import org.example.pointofsalesystem.ui.theme.*
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = koinViewModel(),
    navToRegister: () -> Unit,
    navToForwardPassword: () -> Unit,
) {
    val email by viewModel.loginForm.email.collectAsState()
    val password by viewModel.loginForm.password.collectAsState()

    Row(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Logo(
                width = 200.dp,
                height = 200.dp,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "KANBAN",
                style = TextStyle(
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = MaterialTheme.typography.headlineMedium.fontFamily,
                    fontSize = 47.sp
                ),
                color = LogoColor
            )
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ){
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
            ) {
                Logo(
                    width = 50.dp,
                    height = 50.dp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Heading1SemiBold(
                    text = "Log in to your account",
                    color = grey900,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(10.dp))

                Body1(
                    text = "Welcome back! Please enter your details.",
                    color = grey500,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(40.dp))
                Field(
                    label = "Email",
                    placeholder = "Enter your email",
                    value = email.value,
                    error = email.error,
                    onChange = { viewModel.handleChangeEmail(it) },
                )
                Spacer(modifier = Modifier.height(20.dp))
                FieldPassword(
                    label = "Password",
                    placeholder = "******",
                    value = password.value,
                    error = password.error,
                    onChange = { viewModel.handleChangePassword(it) },
                )
                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically

                ) {
                    CheckboxField(
                        label = "Remember for 30 days",
                        value = viewModel.rememberPassword,
                        onValueChange = {viewModel.handleChangeRememberPassword(it)}
                    )
                    ButtonLink(
                        text = "Forgot password"
                    ){
                        navToForwardPassword()
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                if (!viewModel.errorAuth.isNullOrEmpty()){
                    Body1Medium(
                        text = viewModel.errorAuth!!,
                        color = error500,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                ButtonPrimary(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Sign In",
                    onClick = {
                        viewModel.handleSubmit()
                    },
                    isLoading = viewModel.loadingLogin
                )
                Spacer(modifier = Modifier.height(10.dp))
                ButtonGoogle(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Sign in with Google",
                    onClick = {
                        viewModel.loginGoogle()
                    }
                )
                Spacer(modifier = Modifier.height(40.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Body1Medium(
                        text = "Don’t have an account?",
                        color = grey500
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    ButtonLink(
                        text = "Sign Up"
                    ){
                        navToRegister()
                    }
                }
            }
        }
    }
}