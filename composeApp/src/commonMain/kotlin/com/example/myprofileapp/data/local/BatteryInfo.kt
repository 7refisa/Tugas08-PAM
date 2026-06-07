package com.example.myprofileapp.data.local

expect class BatteryInfo {
    fun getBatteryLevel(): Int
    fun isCharging(): Boolean
}
