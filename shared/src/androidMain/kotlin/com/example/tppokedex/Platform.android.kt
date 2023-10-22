package com.example.tppokedex

import android.content.Context
import com.pokedex.db.AppDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

class AndroidPlatform : Platform {
    override val name: String = "Android ${android.os.Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

actual fun initLogger(){
    Napier.base(DebugAntilog())
}

actual class DatabaseDriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
            AppDatabase.Schema, context,
            "pokedex.db")
    }
}