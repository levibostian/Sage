package earth.levi.sage.service

import com.dropbox.core.oauth.DbxCredential
import earth.levi.sage.store.KeyValueStore
import earth.levi.sage.store.KeyValueStoreKeys
import earth.levi.sage.util.Logger

actual class DropboxHostingService actual constructor(private val keyValueStore: KeyValueStore, logger: Logger): BaseHostingService(keyValueStore, logger) {
    override val accessToken: String?
        get() = "from android"
}