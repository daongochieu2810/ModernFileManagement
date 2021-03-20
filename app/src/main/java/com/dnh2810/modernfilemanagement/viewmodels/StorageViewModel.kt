package com.dnh2810.modernfilemanagement.viewmodels

import android.app.Application
import android.content.ContentUris
import android.media.Image
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.dnh2810.modernfilemanagement.R
import com.dnh2810.modernfilemanagement.models.FileType
import com.dnh2810.modernfilemanagement.models.Video
import java.io.File

class StorageViewModel(application: Application) : AndroidViewModel(application) {
    val path = Environment.getStorageDirectory().absolutePath
    val signal: MutableLiveData<Boolean> = MutableLiveData(false)
    val videos = mutableListOf<Video>()
    val rootFolders = HashSet<String>()

    private val collection = MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL)

    private val projection = arrayOf(
        MediaStore.Video.Media._ID,
        MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
        MediaStore.Video.Media.BUCKET_ID,
        MediaStore.Video.Media.DURATION
    )
    private val directoryProjection = arrayOf(
        MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
        MediaStore.Video.Media.BUCKET_ID,
        MediaStore.Video.Media._ID
    )

    init {
        Log.i("StorageViewModel", "init")
    }

    fun retrieveImages() {

    }
    fun retrieveVideos() {
        val selection = ""
        val selectionArgs = arrayOf<String>()
        // Display videos in alphabetical order based on their display name.
        val sortOrder = "${MediaStore.Video.Media.DISPLAY_NAME} ASC"

        val query = getApplication<Application>().contentResolver.query(
            collection,
            projection,
            selection,
            selectionArgs,
            sortOrder
        )

        query?.use { cursor ->
            // Cache column indices.
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
            val durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)
            val folderIdColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_ID)
            val folderNameColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_DISPLAY_NAME)

            while (cursor.moveToNext()) {
                // Get values of columns for a given video.
                val id = cursor.getLong(idColumn)
                val duration = cursor.getInt(durationColumn)
                val folderId = cursor.getLong(folderIdColumn)
                val folderName = cursor.getString(folderNameColumn)

                val contentUri: Uri = ContentUris.withAppendedId(
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                    id
                )

                val file = File(contentUri.toString())

                // Stores column values and the contentUri in a local object
                // that represents the media file.
                videos += Video(contentUri.toString(), FileType.getFileType(file),
                    file.name, R.drawable.ic_file_icon, file.length(), file.lastModified(), file.extension, duration)

                Log.d("StorageViewModel", file.name + " " + folderId + " " + folderName)
            }

            signal.postValue(true)
        }
    }

    fun queryRootMediaStore() {
        val projection = arrayOf(
            MediaStore.Files.FileColumns.BUCKET_ID,
        )
        getApplication<Application>().contentResolver.query(
            collection,
            projection,
            null,
            null,
            null
        )?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.BUCKET_ID)

            while(cursor.moveToNext()){
                val id = cursor.getLong(idColumn)
                val contentUri: Uri = ContentUris.withAppendedId(
                    MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL),
                    id
                )

                rootFolders.add(contentUri.toString())
            }
            val files = rootFolders.map { File(it) }
            for (file in files) {
                Log.d("StorageViewModels", "file name is " + file.name)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("StorageViewModel", "destroyed")
    }
}