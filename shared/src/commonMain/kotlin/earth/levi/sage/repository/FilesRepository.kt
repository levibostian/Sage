package earth.levi.sage.repository

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import earth.levi.sage.db.Folder
import earth.levi.sage.db.SageDatabase
import kotlinx.coroutines.flow.Flow

interface FilesRepository {
    fun observeFoldersAtPath(path: String): Flow<List<Folder>>
    fun addFolder(folder: Folder)
}

class FilesRepositoryImpl(private val db: SageDatabase): FilesRepository {

    override fun observeFoldersAtPath(path: String): Flow<List<Folder>> {
        return db.folderQueries.selectAll().asFlow().mapToList()
    }

    override fun addFolder(folder: Folder) {
        val charset = "ABCDEFGHIJKLMNOPQRSTUVWXTZabcdefghiklmnopqrstuvwxyz0123456789"
        val randomString = (1..10)
            .map { charset.random() }
            .joinToString("")

        db.folderQueries.insert(randomString, randomString)
    }

}