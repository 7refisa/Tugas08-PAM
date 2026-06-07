package com.example.myprofileapp.data.local

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.net.NetworkInterface

actual class NetworkMonitor {
    actual fun isConnected(): Boolean {
        return try {
            val interfaces = NetworkInterface.getNetworkInterfaces()
            if (interfaces == null) false else {
                interfaces.asSequence().any {
                    it.isUp && !it.isLoopback
                }
            }
        } catch (e: Exception) {
            false
        }
    }

    actual fun observeConnectivity(): Flow<Boolean> = flow {
        while (true) {
            emit(isConnected())
            delay(5000)
        }
    }
}
