package com.example.myprofileapp.di

import app.cash.sqldelight.db.SqlDriver
import com.example.myprofileapp.data.local.DatabaseDriverFactory
import com.example.myprofileapp.data.local.SettingsManager
import com.example.myprofileapp.data.local.createSettings
import com.example.myprofileapp.data.local.DeviceInfo
import com.example.myprofileapp.data.local.NetworkMonitor
import com.example.myprofileapp.data.local.BatteryInfo
import com.russhwolf.settings.ObservableSettings
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single<SqlDriver> {
        DatabaseDriverFactory.appContext = get()
        DatabaseDriverFactory().createDriver()
    }
    single<ObservableSettings> {
        DatabaseDriverFactory.appContext = get()
        createSettings()
    }
    single { DeviceInfo() }
    single { NetworkMonitor(get()) }
    single { BatteryInfo(get()) }
}
