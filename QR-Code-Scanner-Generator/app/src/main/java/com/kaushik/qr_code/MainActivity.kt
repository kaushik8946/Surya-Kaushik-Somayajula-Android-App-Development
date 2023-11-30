package com.kaushik.qr_code

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                val scannerIntent = Intent(applicationContext, Scanner::class.java)
                MakeCard("Click for QR Scanner", scannerIntent)
                val generatorIntent = Intent(applicationContext, Generator::class.java)
                MakeCard("Click for QR Generator", generatorIntent)
            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun MakeCard(value: String, targetIntent: Intent) {
        val context = LocalContext.current
        Card(
            onClick = {
                context.startActivity(targetIntent)
            },
            modifier = Modifier
                .fillMaxWidth(.7f)
                .height(150.dp),
            shape = RoundedCornerShape(40),
            elevation = 20.dp,
            backgroundColor = Color(0xFF53D327)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = value,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
