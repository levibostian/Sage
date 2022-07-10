package earth.levi.sage.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import earth.levi.sage.Greeting
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import earth.levi.sage.android.di.filesViewModel
import earth.levi.sage.android.di.viewModelDiGraph
import earth.levi.sage.android.view.adapter.FolderRecyclerViewAdapter
import earth.levi.sage.di.DiGraph
import earth.levi.sage.di.dropboxHostingService
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val folderRecyclerViewAdapter = FolderRecyclerViewAdapter()

    private val filesViewModel by viewModelDiGraph {
        DiGraph.filesViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<RecyclerView>(R.id.albums_recyclerview).apply {
            adapter = folderRecyclerViewAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        lifecycleScope.launch {
            // hard-coded photo path for now. that way we are guaranteed to find only photos instead of having to browse folders, too.
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                filesViewModel.albumsForPath("/Photos").collect { folders ->
                    folderRecyclerViewAdapter.submitList(folders)
                }
            }
        }

        lifecycleScope.launch {
        //    dropboxApi.getFilesForFolder("/Photos")
        }

        val mainHandler = Handler(Looper.getMainLooper())

        mainHandler.post(object : Runnable {
            override fun run() {
                filesViewModel.addRandomFolder()

                mainHandler.postDelayed(this, 1000)
            }
        })
    }
}
