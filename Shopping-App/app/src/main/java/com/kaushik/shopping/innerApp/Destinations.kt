package com.kaushik.shopping.innerApp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

data class Destinations(
    val route: String,
    val title: String,
    val icon: ImageVector
)

val home = Destinations(
    "home",
    "Home",
    Icons.Outlined.Home
)

val account = Destinations(
    "account",
    "You",
    Icons.Outlined.AccountCircle
)

val cart = Destinations(
    "cart",
    "Cart",
    Icons.Outlined.ShoppingCart
)


val destinationList = listOf(
    home, account, cart
)