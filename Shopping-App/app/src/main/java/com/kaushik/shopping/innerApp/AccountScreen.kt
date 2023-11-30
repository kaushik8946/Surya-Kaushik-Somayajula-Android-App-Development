package com.kaushik.shopping.innerApp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.kaushik.shopping.signIn.UserData

@Composable
fun AccountScreen(navController: NavHostController, userdata: UserData?) {
    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun MakeCard(text: String, path: String) {
        Card(
            onClick = {
                navController.navigate(path)
            },
            shape = RoundedCornerShape(30.dp),
            elevation = 10.dp,
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth(.8f),
            backgroundColor = Color.Gray
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = text,
                    textAlign = TextAlign.Center,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF4D3AA2)),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MakeCard(text = "Account Details", path = "details")
        MakeCard(text = "Your Cart", path = cart.route)
        MakeCard(text = "Your orders", path = "orders")
    }
}