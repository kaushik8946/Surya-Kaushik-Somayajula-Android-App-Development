package com.kaushik.shopping.innerApp

import com.kaushik.shopping.R

data class Mobile(
    val company: String = "",
    val model: String = "",
    val photo: Int = 0,
    val ram: Int = 0,
    val rom: Int = 0,
    val price: Int = 0
)

val mobilesList = listOf(
    Mobile(
        company = "Samsung",
        model = "Galaxy S23 Ultra",
        photo = R.drawable.galaxy_s23_ultra,
        ram = 12,
        rom = 1024,
        price = 100550
    ),
    Mobile(
        company = "Apple",
        model = "iPhone 15 Pro Max",
        photo = R.drawable.iphone_15_pro_max,
        ram = 8,
        rom = 512,
        price = 159900
    ),
    Mobile(
        company = "Google",
        model = "Pixel 8 Pro",
        photo = R.drawable.pixel_8_pro,
        ram = 12,
        rom = 256,
        price = 106999
    ),
    Mobile(
        company = "OnePlus",
        model = "11 Pro",
        photo = R.drawable.oneplus_11_pro,
        ram = 8,
        rom = 256,
        price = 39989
    ),
    Mobile(
        company = "Xiaomi",
        model = "13 Pro",
        photo = R.drawable.xiaomi_13_pro,
        ram = 12,
        rom = 512,
        price = 32999
    ),
    Mobile(
        company = "Motorola",
        model = "Edge+",
        photo = R.drawable.motorola_edge,
        ram = 10,
        rom = 256,
        price = 23999
    ),
    Mobile(
        company = "Realme",
        model = "GT Neo 3",
        photo = R.drawable.realme_gt_neo_3,
        ram = 12,
        rom = 256,
        price = 21999
    ),
    Mobile(
        company = "Asus",
        model = "ROG Phone 6 Pro",
        photo = R.drawable.asus_rog_phone_6_pro,
        ram = 16,
        rom = 512,
        price = 59999
    ),
    Mobile(
        company = "Huawei",
        model = "Mate 50 Pro",
        photo = R.drawable.huawei_mate_50_pro,
        ram = 8,
        rom = 256,
        price = 49246
    ),
    Mobile(
        company = "Nokia",
        model = "X10",
        photo = R.drawable.nokia_x10,
        ram = 6,
        rom = 128,
        price = 13999
    )
)
