package com.kaushik.shopping.innerApp

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.database.DatabaseReference
import com.kaushik.shopping.outerApp.SpacerWidth

@Composable
fun HomeScreen(
    cartRef: DatabaseReference,
    navController: NavHostController
) {
    val context = LocalContext.current
    fun addToOrderFromHome(mobile: Mobile) {

        val order = Order(
            mobile = mobile,
            orderDateLong = getCurrentDateTimeInMillis(),
            orderDateString = longToDateTimeString(getCurrentDateTimeInMillis()),
            status = getRandomOrderStatus()
        )
        val data = context.getSharedPreferences("order", Context.MODE_PRIVATE)
        cartRef.child("${mobile.company} ${mobile.model}").removeValue()
        cartList.value -= mobile
        data.edit().putString("company", mobile.company).commit()
        data.edit().putString("model", mobile.model).commit()
        data.edit().putInt("photo", mobile.photo).commit()
        data.edit().putInt("ram", mobile.ram).commit()
        data.edit().putInt("rom", mobile.rom).commit()
        data.edit().putInt("price", mobile.price).commit()
        navController.navigate("checkout")
    }
    fun addItemToCart(mobile: Mobile) {
        cartRef.child("${mobile.company} ${mobile.model}").setValue(mobile)
            .addOnSuccessListener {
                Toast.makeText(context, "Added to Cart", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { error ->
                Toast.makeText(context, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
    }
    Column(
        modifier = Modifier
//            .background(Color.)
            .padding(horizontal = 10.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        mobilesList.forEach { mobile ->
            Row(
                modifier = Modifier
//                    .background(Color.White)
                    .fillMaxWidth()
                    .height(150.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = mobile.photo),
                    contentDescription = mobile.model,
                    modifier = Modifier.size(100.dp)
                )
                Column {
                    Text(
                        text = "${mobile.company} ${mobile.model}",
                        fontSize = 20.sp
                    )
                    Text(
                        text = "${mobile.ram}GB RAM ${mobile.rom}GB Storage",
                        fontSize = 20.sp
                    )
                    Text(text = "Price: ${mobile.price}", fontSize = 20.sp)
                    Row {
                        Button(
                            onClick = {
                                addItemToCart(mobile)
                            }
                        ) {
                            Text(text = "Add to cart", color = Color.White)
                        }
                        SpacerWidth(20)
                        Button(
                            onClick = {
                                addToOrderFromHome(mobile)
                            }
                        ) {
                            Text(text = "Buy now", color = Color.White)
                        }
                    }
                }
            }
        }
    }
}
