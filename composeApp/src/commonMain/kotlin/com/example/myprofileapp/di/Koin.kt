package com.example.myprofileapp.di

import org.koin.core.context.startKoin
import org.koin.core.context.GlobalContext
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.dsl.KoinAppDeclaration
import com.example.myprofileapp.db.NotesDatabase
import com.example.myprofileapp.data.NoteRepository
import com.example.myprofileapp.data.local.SettingsManager
import com.example.myprofileapp.viewmodel.NotesViewModel
import com.example.myprofileapp.viewmodel.ProfileViewModel

expect val platformModule: Module

val commonModule = module {
    // Database
    single { NotesDatabase(get()) }
    
    // Repository
    single { NoteRepository(get()) }
    
    // SettingsManager
    single { SettingsManager(get()) }
    
    // ViewModels
    factory { NotesViewModel(get(), get()) }
    factory { ProfileViewModel(get()) }
}

fun initKoin(appDeclaration: KoinAppDeclaration = {}) {
    if (GlobalContext.getOrNull() == null) {
        startKoin {
            appDeclaration()
            modules(commonModule + platformModule)
        }
    }
}
