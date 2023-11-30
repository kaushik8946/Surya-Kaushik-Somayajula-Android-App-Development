package com.kaushik.shopping.innerApp

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

var cartList = mutableStateOf(listOf<Mobile>())

@Composable
fun CartScreen(
    cartRef: DatabaseReference,
    navController: NavHostController
) {
    val context = LocalContext.current
    fun removeFromCart(mobile: Mobile) {
        cartRef.child("${mobile.company} ${mobile.model}").removeValue()
            .addOnSuccessListener {
                Toast.makeText(context, "Item removed from Cart", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { error ->
                Toast.makeText(context, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        cartList.value -= mobile
    }

    fun addToOrderFromCart(mobile: Mobile) {
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
    cartList.value = listOf()
    cartRef.addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            for (mobileSnapshot in snapshot.children) {
                val mobile = mobileSnapshot.getValue(Mobile::class.java)
                if (mobile != null) {
                    Log.i("firebase", mobile.toString())
                    if (mobile !in cartList.value) {
                        cartList.value += mobile
                        Log.i("Firebase", "cart list is ${cartList.value}")
                    }
                }
            }
        }

        override fun onCancelled(error: DatabaseError) {
            Log.e("Firebase", "Error getting data", error.toException())
        }

    })
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp)
    ) {
        items(cartList.value) { mobile ->
            Row(
                modifier = Modifier
                    .background(Color.White)
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
                    Row {
                        Button(
                            onClick = {
                                removeFromCart(mobile)
                            }
                        ) {
                            Text(text = "Remove from cart", color = Color.White)
                        }

                        Button(
                            onClick = {
                                addToOrderFromCart(mobile)
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
