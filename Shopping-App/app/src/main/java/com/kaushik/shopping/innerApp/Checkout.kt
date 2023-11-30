package com.kaushik.shopping.innerApp

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.database.DatabaseReference

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Checkout(
    orderRef: DatabaseReference,
    cartRef: DatabaseReference,
    navController: NavHostController
) {
    val context = LocalContext.current
    val data = context.getSharedPreferences("order", Context.MODE_PRIVATE)
    val mobile = Mobile(
        data.getString("company", "").toString(),
        data.getString("model", "").toString(),
        data.getInt("photo", 0),
        data.getInt("ram", 0),
        data.getInt("rom", 0),
        data.getInt("price", 0)
    )
    var name by remember {
        mutableStateOf("")
    }
    var addressLine1 by remember {
        mutableStateOf("")
    }
    var addressLine2 by remember {
        mutableStateOf("")
    }
    var city by remember {
        mutableStateOf("")
    }
    var pinCode by remember {
        mutableIntStateOf(0)
    }
    var state by rememberSaveable { mutableStateOf(statesAndUnionTerritories[0]) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(vertical = 10.dp, horizontal = 20.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        val enumValues = Payment.values()
        var selectedPayment by remember {
            mutableStateOf(enumValues[0])
        }
        Text(text = "Price: ${mobile.price}", fontSize = 20.sp)
        Text(text = "Select payment method", fontSize = 20.sp)
        Column {
            enumValues.forEachIndexed { _, payment ->
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selectedPayment == payment,
                        onClick = { selectedPayment = payment }
                    )
                    Text(text = payment.toString())
                }
            } //check boxes
            Text(text = "Enter Address", fontSize = 20.sp)
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    singleLine = true,
                    label = { Text(text = "Enter name:") },
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .height(70.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp)
                )
                OutlinedTextField(
                    value = addressLine1,
                    onValueChange = { addressLine1 = it },
                    minLines = 2,
                    label = { Text(text = "Address Line 1:") },
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .height(100.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp)
                )
                OutlinedTextField(
                    value = addressLine2,
                    onValueChange = { addressLine2 = it },
                    minLines = 2,
                    label = { Text(text = "Address Line 2:") },
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .height(100.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp)
                )
                OutlinedTextField(
                    value = city,
                    onValueChange = { city = it },
                    singleLine = true,
                    label = { Text(text = "Enter City:") },
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .height(70.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp)
                )
                var expanded by rememberSaveable { mutableStateOf(false) }
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = """
                            Select
                            State:
                        """.trimIndent(),
                        modifier = Modifier.align(Alignment.CenterStart),
                        textAlign = TextAlign.Left,
                        fontSize = 15.sp
                    )
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = {
                            expanded = !expanded
                        },
                        modifier = Modifier.align(Alignment.CenterEnd)
                    ) {
                        OutlinedTextField(
                            readOnly = true,
                            value = state,
                            onValueChange = { },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = expanded
                                )
                            },
                            colors = ExposedDropdownMenuDefaults.textFieldColors(),
                            modifier = Modifier.padding(10.dp)
                        )
                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = {
                                expanded = false
                            }
                        ) {
                            statesAndUnionTerritories.forEach { selectionOption ->
                                DropdownMenuItem(
                                    onClick = {
                                        state = selectionOption
                                        expanded = false
                                    }
                                ) {
                                    Text(text = selectionOption)
                                }
                            }
                        }
                    }
                } //drop down
                OutlinedTextField(
                    value = pinCode.toString(),
                    onValueChange = {
                        pinCode =
                            if (it.isEmpty()) 0
                            else it.toDouble().toInt()
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = !isValidPinCode(pinCode),
                    label = { Text(text = "Enter pin code:") },
                    modifier = Modifier.padding(vertical = 10.dp)
                )
            } // all fields
        }
        Box(modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = {
                    if (name.trim().isEmpty()) {
                        Toast.makeText(context, "Enter name!", Toast.LENGTH_SHORT).show()
                    } else if (addressLine1.trim().isEmpty() && addressLine2.trim().isEmpty()) {
                        Toast.makeText(context, "Enter Address!", Toast.LENGTH_SHORT).show()
                    } else if (city.trim().isEmpty()) {
                        Toast.makeText(context, "Enter city!", Toast.LENGTH_SHORT).show()
                    } else if (!isValidPinCode(pinCode)) {
                        Toast.makeText(
                            context,
                            "Enter a correct 6-digit pin code!",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        val address = Address(
                            name,
                            addressLine1,
                            addressLine2,
                            city,
                            state,
                            pinCode
                        )
                        val order = Order(
                            mobile = mobile,
                            orderDateLong = getCurrentDateTimeInMillis(),
                            orderDateString = longToDateTimeString(getCurrentDateTimeInMillis()),
                            status = getRandomOrderStatus(),
                            payment = selectedPayment,
                            address = address
                        )

                        orderRef.child("${mobile.company} ${mobile.model}").setValue(order)
                            .addOnSuccessListener {
                                Toast.makeText(context, "Item ordered", Toast.LENGTH_SHORT).show()
                            }
                            .addOnFailureListener { error ->
                                Toast.makeText(
                                    context,
                                    "Error: ${error.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        cartRef.child("${mobile.company} ${mobile.model}").removeValue()
                        navController.navigate(home.route)
                    }
                },
                modifier = Modifier.align(Alignment.Center)
            ) {
                Text(text = "Place order")
            }
        }
    }
}

fun isValidPinCode(pinCode: Int): Boolean {
    return pinCode in 100000..999999
}