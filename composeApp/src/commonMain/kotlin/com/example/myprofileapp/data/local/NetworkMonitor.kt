package com.example.myprofileapp.data.local

import kotlinx.coroutines.flow.Flow

expect class NetworkMonitor {
    fun isConnected(): Boolean
    fun observeConnectivity(): Flow<Boolean>
}
