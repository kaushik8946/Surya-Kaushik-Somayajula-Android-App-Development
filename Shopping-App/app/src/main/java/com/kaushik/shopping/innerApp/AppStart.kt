package com.kaushik.shopping.innerApp

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.kaushik.shopping.signIn.GoogleAuthUIClient
import com.kaushik.shopping.signIn.UserData

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppStart(
    userdata: UserData?,
    onSignOut: () -> Unit,
    googleAuthUIClient: GoogleAuthUIClient
) {

    val navController = rememberNavController()
    val dbRef = Firebase.database.reference
    var cartRef = dbRef
    var orderRef = dbRef
    if (userdata != null) {
        cartRef = cartRef.child("Cart ${removeSpecialCharacters(userdata.email!!)}")
        orderRef = orderRef.child("Orders ${removeSpecialCharacters(userdata.email)}")
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Welcome ${userdata?.username}",
                        fontSize = 20.sp
                    )
                },
                actions = {
                    Button(
                        onClick = { onSignOut.invoke() },
                        modifier = Modifier.padding(end = 10.dp)
                    ) {
                        Text(text = "SignOut")
                    }
                },
                backgroundColor = Color(0xFFFF9100)
            )
        },
        bottomBar = {
            MyBottomNavigation(navController)
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            NavHost(navController = navController, startDestination = home.route) {
                composable(home.route) {
                    HomeScreen(cartRef, navController)
                }
                composable(account.route) {
                    AccountScreen(navController, userdata)
                }
                composable(cart.route) {
                    CartScreen(cartRef, navController)
                }
                composable("orders") {
                    Orders(orderRef)
                }
                composable("details") {
                    Details(userdata)
                }
                composable("checkout") {
                    Checkout(orderRef, cartRef, navController)
                }
            }
        }
    }
}

@Composable
fun MyBottomNavigation(navController: NavHostController) {
    val selectedIndex = rememberSaveable { mutableStateOf(0) }
    BottomNavigation(
        backgroundColor = Color.White,
        contentColor = Color.Black
    ) {
        destinationList.forEachIndexed { index, destination ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        imageVector = destination.icon,
                        modifier = Modifier.height(25.dp),
                        contentDescription = destination.title,
                        tint = Color.Black
                    )
                },
                selected = index == selectedIndex.value,
                onClick = {
                    selectedIndex.value = index
                    navController.navigate(destinationList[index].route) {
                        popUpTo(home.route)
                        launchSingleTop = true
                    }
                },
                label = {
                    Text(text = destination.title)
                }
            )
        }
    }
}

fun removeSpecialCharacters(inputString: String): String {
    val specialCharacters = setOf('.', '#', '$', '[', ']')
    return inputString.filter { char -> char !in specialCharacters }
}