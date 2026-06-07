package com.example.myprofileapp.utils

import platform.Foundation.NSDate
import platform.Foundation.NSDateFormatter
import platform.Foundation.NSLocale
import platform.Foundation.dateWithTimeIntervalSince1970

actual fun formatEpochMillis(epochMillis: Long): String {
    val date = NSDate.dateWithTimeIntervalSince1970(epochMillis / 1000.0)
    val formatter = NSDateFormatter()
    formatter.dateFormat = "dd MMM yyyy, HH:mm"
    formatter.locale = NSLocale("id_ID")
    return formatter.stringFromDate(date)
}
