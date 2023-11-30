package com.kaushik.quizapp

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun ResultScreen(navController: NavHostController) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE)
    val scoreInt = sharedPreferences.getInt("score", 0)
    val scoreFloat: Float = scoreInt.toFloat() / 10
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box {
            CircularProgressIndicator(
                progress = scoreFloat,
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.Center),
                strokeCap = StrokeCap.Round,
                strokeWidth = 15.dp,
                color = Color.Green,
                trackColor = Color.Gray
            )
            Text(
                text = """
            Score is
            ${(scoreFloat * 100).toInt()}%
        """.trimIndent(),
                modifier = Modifier.align(Alignment.Center),
                fontSize = 30.sp,
                textAlign = TextAlign.Center
            )
        }
        Button(
            onClick = { navController.navigate("home") }
        ) {
            Text(text = "Go to home")
        }
    }
}