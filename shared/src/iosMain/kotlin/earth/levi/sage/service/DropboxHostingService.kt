package earth.levi.sage.service

import cocoapods.ObjectiveDropboxOfficial.*
import earth.levi.sage.Secrets
import earth.levi.sage.db.File
import earth.levi.sage.db.Folder
import earth.levi.sage.store.KeyValueStore
import earth.levi.sage.type.FolderContents
import earth.levi.sage.util.Logger
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

interface iOSHostingService: HostingService {
    fun initialize()
    fun login()
}

class DropboxHostingService(): iOSHostingService {

    override fun initialize() {
        DBClientsManager.setupWithAppKey(Secrets.dropboxAppKey)
    }

    override fun login() {
    }

    override suspend fun getFolderContents(path: String): Result<FolderContents> {
        val client = DBClientsManager.authorizedClient() ?: return Result.failure(RuntimeException("")) // TODO replace runtime exception with somehting that means they need to login

        return suspendCoroutine { coroutine ->
            fun getContentsFromResponse(entries: List<DBFILESMetadata>): FolderContents {
                val subfolders = entries.map {
                    val folderMetadata = it as? DBFILESFileMetadata ?: return@map null
                    return@map Folder(
                        name = folderMetadata.name,
                        path = folderMetadata.pathDisplay!!
                    )
                }.filterNotNull()

                val files = entries.map {
                    val fileMetadata = it as? DBFILESFolderMetadata ?: return@map null
                    return@map File(
                        name = fileMetadata.name,
                        path = fileMetadata.pathDisplay!!
                    )
                }.filterNotNull()

                // Note: entry can also be of type DBFILESDeletedMetadata

                return FolderContents(subfolders = subfolders, files = files)
            }

            client.filesRoutes.listFolder(path).setResponseBlock { result, routeError, networkError ->
                routeError?.let { (it as? Throwable)?.let { coroutine.resumeWith(Result.failure(it)) }}
                networkError?.let { (it as? Throwable)?.let { coroutine.resumeWith(Result.failure(it)) }}

                val accumulativeFiles = mutableListOf<File>()
                val accumulativeFolders = mutableListOf<Folder>()

                var response = result as DBFILESListFolderResult

                var hasMore = response.hasMore.boolValue
                var cursor = response.cursor

                val folderContents = getContentsFromResponse(response.entries as List<DBFILESMetadata>)
                accumulativeFiles.addAll(folderContents.files)
                accumulativeFolders.addAll(folderContents.subfolders)

                while (hasMore) {
                    client.filesRoutes.listFolderContinue(cursor).setResponseBlock { result, routeError, networkError ->
                        routeError?.let { (it as? Throwable)?.let { coroutine.resumeWith(Result.failure(it)) }}
                        networkError?.let { (it as? Throwable)?.let { coroutine.resumeWith(Result.failure(it)) }}

                        response = result as DBFILESListFolderResult

                        hasMore = response.hasMore.boolValue
                        cursor = response.cursor

                        val folderContents = getContentsFromResponse(response.entries as List<DBFILESMetadata>)
                        accumulativeFiles.addAll(folderContents.files)
                        accumulativeFolders.addAll(folderContents.subfolders)
                    }
                }

                coroutine.resume(Result.success(FolderContents(subfolders = accumulativeFolders, files = accumulativeFiles)))
            }
        }
    }

}