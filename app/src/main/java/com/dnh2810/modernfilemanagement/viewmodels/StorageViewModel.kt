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
import com.dnh2810.modernfilemanagement.R
import com.dnh2810.modernfilemanagement.models.FileType
import com.dnh2810.modernfilemanagement.models.Video
import java.io.File

class StorageViewModel(application: Application) : AndroidViewModel(application) {
    val path = Environment.getStorageDirectory().absolutePath
    val videos = mutableListOf<Video>()

    private val collection =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Video.Media.getContentUri(
                MediaStore.VOLUME_EXTERNAL
            )
        } else {
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        }
    private val projection = arrayOf(
        MediaStore.Video.Media._ID,
        MediaStore.Video.Media.DISPLAY_NAME,
        MediaStore.Video.Media.DURATION,
        MediaStore.Video.Media.SIZE,
        MediaStore.Video.Media.MIME_TYPE
    )
    private val directoryProjection = arrayOf(
        MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
        MediaStore.Video.Media.BUCKET_ID,
        MediaStore.Video.Media._ID
    )

    init {
        Log.i("StorageViewModel", "init")
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
            val nameColumn =
                cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)
            val durationColumn =
                cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)
            val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)
            val extensionColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE)

            while (cursor.moveToNext()) {
                // Get values of columns for a given video.
                val id = cursor.getLong(idColumn)
                val name = cursor.getString(nameColumn)
                val duration = cursor.getInt(durationColumn)
                val size = cursor.getLong(sizeColumn)
                val extension = cursor.getString(extensionColumn)

                val contentUri: Uri = ContentUris.withAppendedId(
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                    id
                )

                val file = File(contentUri.toString())

                // Stores column values and the contentUri in a local object
                // that represents the media file.
                videos += Video(contentUri.toString(), FileType.getFileType(file),
                    name, R.drawable.file_icon, size, file.lastModified(), extension, duration)

                Log.d("StorageViewModel", name)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("GameViewModel", "destroyed")
    }
}