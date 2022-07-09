package earth.levi.sage.di

import com.russhwolf.settings.AppleSettings
import earth.levi.sage.store.KeyValueStore
import earth.levi.sage.util.Logger
import earth.levi.sage.util.LoggerImpl
import platform.Foundation.NSUserDefaults

actual val DiGraph.keyValueStore: KeyValueStore
    get() = AppleSettings(NSUserDefaults())

actual val DiGraph.logger: Logger
    get() = LoggerImpl()