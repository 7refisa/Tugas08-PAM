package com.example.myprofileapp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.example.myprofileapp.di.initKoin
import org.koin.compose.KoinContext

fun main() {
    initKoin()

    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "MyProfileApp",
        ) {
            KoinContext {
                App()
            }
        }
    }
}