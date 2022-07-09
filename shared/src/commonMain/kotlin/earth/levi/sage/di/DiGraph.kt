package earth.levi.sage.di

import earth.levi.sage.service.DropboxHostingService
import earth.levi.sage.store.KeyValueStore
import earth.levi.sage.util.Logger

object DiGraph

expect val DiGraph.keyValueStore: KeyValueStore

val DiGraph.dropboxHostingService: DropboxHostingService
    get() = DropboxHostingService(keyValueStore, logger)

expect val DiGraph.logger: Logger