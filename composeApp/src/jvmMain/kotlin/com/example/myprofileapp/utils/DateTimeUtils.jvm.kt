package com.example.myprofileapp.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

actual fun formatEpochMillis(epochMillis: Long): String {
    val sdf = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale("id", "ID"))
    return sdf.format(Date(epochMillis))
}
