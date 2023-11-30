package com.kaushik.shopping.outerApp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.kaushik.shopping.R
import com.kaushik.shopping.signIn.SignInState

@Composable
fun SignInScreen(
    navController: NavHostController,
    state: SignInState,
    onSignInClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF4D3AA2)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Sign In with Google",
            fontSize = 50.sp,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center,
            color = Color(0xFFC74E63)
        )
        SpacerHeight()
        Image(
            painter = painterResource(id = R.drawable.google_logo),
            contentDescription = "google",
            modifier = Modifier.size(50.dp)
        )
        SpacerHeight()
        Button(
            onClick = onSignInClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFFF00))
        ) {
            Text(
                text = "Sign In",
                color = Color(0xFF4D3AA2),
                fontWeight = FontWeight.Bold
            )
        }
    }
}