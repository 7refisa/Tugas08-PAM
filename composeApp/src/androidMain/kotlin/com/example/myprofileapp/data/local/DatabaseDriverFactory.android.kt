package com.example.myprofileapp.data.local

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.example.myprofileapp.db.NotesDatabase

actual class DatabaseDriverFactory {
    companion object {
        var appContext: Context? = null
    }

    actual fun createDriver(): SqlDriver {
        val context = appContext ?: throw IllegalStateException("Android Context not initialized. Set DatabaseDriverFactory.appContext in MainActivity.")
        return AndroidSqliteDriver(NotesDatabase.Schema, context, "notes.db")
    }
}
