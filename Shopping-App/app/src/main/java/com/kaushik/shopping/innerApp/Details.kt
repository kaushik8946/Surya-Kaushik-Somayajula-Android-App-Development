package com.kaushik.shopping.innerApp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.kaushik.shopping.signIn.UserData

@Composable
fun Details(userdata: UserData?) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (userdata != null) {
            if (userdata.profilePictureUrl != null) {
                AsyncImage(
                    model = userdata.profilePictureUrl,
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }
            MakeBox(key = "Name", value = userdata.username)
            MakeBox(key = "Email", value = userdata.email)
        }
    }
}

@Composable
fun MakeBox(
    key: String,
    value: String?,
    height: Dp = 40.dp,
    background: Color = Color.White
) {
    Box(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .height(height)
            .background(background)
            .clip(shape = RoundedCornerShape(20.dp))
    ) {
        Text(
            text = "$key: $value",
            modifier = Modifier.align(Alignment.CenterStart)
        )
    }
}