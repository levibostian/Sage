package earth.levi.sage.service

import com.russhwolf.settings.get
import earth.levi.sage.store.KeyValueStore
import earth.levi.sage.util.Logger

/**
 * Defines actions for a hosting service for photos such as Dropbox, Samba share, etc.
 */
interface HostingService {
    // in the future, may get rid of this property because something like samba share might not use an access token?
    val accessToken: String?
}

abstract class BaseHostingService(private val keyValueStore: KeyValueStore, private val logger: Logger): HostingService {
    fun getFilesForPath(path: String): List<String> {
        logger.verbose("trying to save data.")
        val currentValue = keyValueStore.getStringOrNull("foo")
        logger.verbose("current before save: $currentValue")

        keyValueStore.putString("foo", this.accessToken!!)

        val value: String = keyValueStore.getString("foo")
        logger.verbose("current after save: $value")

        return emptyList()
    }
}