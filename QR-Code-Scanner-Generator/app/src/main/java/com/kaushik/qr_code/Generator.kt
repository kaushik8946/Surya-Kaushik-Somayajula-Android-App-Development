package com.kaushik.qr_code

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class Generator : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var inputText by rememberSaveable {
                mutableStateOf("")
            }

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                OutlinedTextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    label = {
                        Text(text = "Enter text:")
                    },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.height(60.dp)
                )
                SpacerHeight(50.dp)
                Button(
                    onClick = {
                        if (inputText.isEmpty()) {
                            Toast.makeText(
                                applicationContext,
                                "Enter text",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            setContent {
                                val bitmapImage = rememberQrBitmapPainter(content = inputText)
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "Result QR is",
                                        fontSize = 30.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    SpacerHeight()
                                    Image(
                                        painter = bitmapImage,
                                        contentDescription = null,
                                        modifier = Modifier.size(200.dp)
                                    )
                                }
                            }
                        }
                    },
                    shape = RoundedCornerShape(40)
                ) {
                    Text(
                        text = "Create",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}