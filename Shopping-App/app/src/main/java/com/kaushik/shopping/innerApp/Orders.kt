package com.kaushik.shopping.innerApp

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Random
import java.util.TimeZone

@Composable
fun Orders(orderRef: DatabaseReference) {
    val context = LocalContext.current
    LaunchedEffect(key1 = "") {
        orderList.value = listOf()
        orderRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (orderSnapshot in snapshot.children) {
                    val order = orderSnapshot.getValue(Order::class.java)
                    if (order != null) {
                        if (order !in orderList.value) {
                            orderList.value += order
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Error getting order data", error.toException())
            }

        })
    }
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp)
    ) {
        items(orderList.value) { order ->
            val mobile = order.mobile
            Row(
                modifier = Modifier
//                    .background(Color.White)
                    .fillMaxWidth(),
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
                        fontSize = 20.sp,
                    )
                    Text(
                        text = "${mobile.ram}GB RAM ${mobile.rom}GB Storage",
                        fontSize = 20.sp
                    )
                    Text(
                        text = "Price: ${mobile.price}",
                        fontSize = 20.sp
                    )
                    Text(
                        text = "Order status: ${order.status}",
                        fontSize = 20.sp
                    )
                    Text(
                        text = "Payment Method: ${order.payment}",
                        fontSize = 20.sp
                    )
                    Button(onClick = {
                        orderRef.child("${mobile.company} ${mobile.model}").removeValue()
                            .addOnSuccessListener {
                                Toast.makeText(context, "Order Cancelled", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        orderList.value -= order
                    }) {
                        Text(text = "Cancel Order")
                    }
                }
            }
        }
    }
}

val orderList = mutableStateOf(listOf<Order>())

data class Order(
    val mobile: Mobile = Mobile(),
    val orderDateLong: Long = 0,
    val orderDateString: String = "",
    var status: OrderStatus = getRandomOrderStatus(),
    val payment: Payment = Payment.CASH,
    var address: Address = Address()
)

enum class OrderStatus {
    ORDER_PLACED,
    DISPATCHED,
    SHIPPED,
    OUT_FOR_DELIVERY
}

enum class Payment {
    CARD,
    CASH,
    UPI,
    NET_BANKING
}

data class Address(
    val name: String = "",
    val addressLine1: String = "",
    val addressLine2: String = "",
    val city: String = "",
    val state: String = "",
    val pinCode: Int = 0
)

val statesAndUnionTerritories = listOf(
    "Andhra Pradesh",
    "Arunachal Pradesh",
    "Assam",
    "Bihar",
    "Chhattisgarh",
    "Goa",
    "Gujarat",
    "Haryana",
    "Himachal Pradesh",
    "Jammu and Kashmir",
    "Jharkhand",
    "Karnataka",
    "Kerala",
    "Madhya Pradesh",
    "Maharashtra",
    "Manipur",
    "Meghalaya",
    "Mizoram",
    "Nagaland",
    "Odisha",
    "Punjab",
    "Rajasthan",
    "Sikkim",
    "Tamil Nadu",
    "Telangana",
    "Tripura",
    "Uttar Pradesh",
    "Uttarakhand",
    "West Bengal",
    "Andaman and Nicobar Islands",
    "Chandigarh",
    "Dadra and Nagar Haveli",
    "Daman and Diu",
    "Delhi",
    "Lakshadweep",
    "Puducherry"
)


fun getCurrentDateTimeInMillis(): Long {
    return System.currentTimeMillis()
}

@SuppressLint("SimpleDateFormat")
fun longToDateTimeString(timestamp: Long): String {
    val formatter = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
    formatter.timeZone = TimeZone.getTimeZone("Asia/Kolkata")
    return formatter.format(Date(timestamp))
}

fun getRandomOrderStatus(): OrderStatus {
    val enumValues = OrderStatus.values()
    val randomIndex = Random().nextInt(enumValues.size)
    return enumValues[randomIndex]
}
