package earth.levi.sage.service

import earth.levi.sage.store.KeyValueStore
import earth.levi.sage.util.Logger

actual class DropboxHostingService actual constructor(keyValueStore: KeyValueStore, logger: Logger): BaseHostingService(keyValueStore, logger) {
    override val accessToken: String?
        get() = "from ios"
}