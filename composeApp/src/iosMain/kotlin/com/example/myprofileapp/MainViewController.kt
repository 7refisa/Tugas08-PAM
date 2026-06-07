package com.example.myprofileapp

import androidx.compose.ui.window.ComposeUIViewController
import com.example.myprofileapp.di.initKoin
import org.koin.compose.KoinContext

fun MainViewController() = ComposeUIViewController {
    initKoin()
    KoinContext {
        App()
    }
}