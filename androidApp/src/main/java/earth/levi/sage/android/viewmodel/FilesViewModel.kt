package earth.levi.sage.android.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import earth.levi.sage.db.Folder
import earth.levi.sage.repository.FilesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class FilesViewModel(private val filesRepository: FilesRepository): ViewModel() {

    fun albumsForPath(path: String): Flow<List<Folder>> = filesRepository.observeFoldersAtPath(path)

    fun addRandomFolder() {
        filesRepository.addFolder(Folder("foo", "bar"))
    }

}