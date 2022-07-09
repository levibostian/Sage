@file:JvmName("DiGraphAndroid")
package earth.levi.sage.di

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.russhwolf.settings.AndroidSettings
import earth.levi.sage.store.KeyValueStore
import earth.levi.sage.util.Logger
import earth.levi.sage.util.LoggerImpl

/**
 * To avoid the di graph holding a strong reference to Context (lint says this error prone to do),
 * this singleton will be called when the application is loaded and will take what it needs from
 * the Context.
 */
object ContextDependents {
    lateinit var sharedPreferences: SharedPreferences

    fun initialize(context: Context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    }
}

actual val DiGraph.keyValueStore: KeyValueStore
    get() = AndroidSettings(ContextDependents.sharedPreferences)

actual val DiGraph.logger: Logger
    get() = LoggerImpl()