package earth.levi.sage.service

import earth.levi.sage.store.KeyValueStore
import earth.levi.sage.util.Logger

expect class DropboxHostingService constructor(keyValueStore: KeyValueStore, logger: Logger): BaseHostingService