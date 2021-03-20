package com.dnh2810.modernfilemanagement.files_utils

import android.database.Cursor
import android.database.MatrixCursor
import android.os.*
import android.provider.DocumentsContract
import android.provider.DocumentsProvider
import android.util.Log
import com.dnh2810.modernfilemanagement.R
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException

class MainDocumentsProvider: DocumentsProvider() {
    val DEFAULT_ROOT_PROJECTION: Array<String> = arrayOf(
        DocumentsContract.Root.COLUMN_ROOT_ID,
        DocumentsContract.Root.COLUMN_MIME_TYPES,
        DocumentsContract.Root.COLUMN_FLAGS,
        DocumentsContract.Root.COLUMN_ICON,
        DocumentsContract.Root.COLUMN_TITLE,
        DocumentsContract.Root.COLUMN_DOCUMENT_ID,
        DocumentsContract.Root.COLUMN_SUMMARY,
        DocumentsContract.Root.COLUMN_AVAILABLE_BYTES
    )
    val DEFAULT_DOCUMENT_PROJECTION: Array<String> = arrayOf(
        DocumentsContract.Document.COLUMN_DOCUMENT_ID,
        DocumentsContract.Document.COLUMN_MIME_TYPE,
        DocumentsContract.Document.COLUMN_DISPLAY_NAME,
        DocumentsContract.Document.COLUMN_LAST_MODIFIED,
        DocumentsContract.Document.COLUMN_FLAGS,
        DocumentsContract.Document.COLUMN_SIZE
    )

    override fun onCreate(): Boolean {
        return true
    }

    override fun queryRoots(projection: Array<out String>?): Cursor {
        val result = MatrixCursor(projection)
        val file = File(Environment.getExternalStorageDirectory().absolutePath)

        result.newRow().apply {
            add(DocumentsContract.Root.COLUMN_ROOT_ID, file.absolutePath)

            add(DocumentsContract.Root.COLUMN_SUMMARY, "SOMETHING")

            add(
                DocumentsContract.Root.COLUMN_FLAGS,
                DocumentsContract.Root.FLAG_SUPPORTS_CREATE or
                        DocumentsContract.Root.FLAG_SUPPORTS_RECENTS or
                        DocumentsContract.Root.FLAG_SUPPORTS_SEARCH
            )

            add(DocumentsContract.Root.COLUMN_TITLE,"SOME")

            add(DocumentsContract.Root.COLUMN_DOCUMENT_ID, file.absolutePath)

            add(DocumentsContract.Root.COLUMN_MIME_TYPES, file.extension)
            add(DocumentsContract.Root.COLUMN_AVAILABLE_BYTES, file.freeSpace)
            add(DocumentsContract.Root.COLUMN_ICON, R.drawable.ic_file_icon)
        }

        return result
    }

    override fun queryChildDocuments(
        parentDocumentId: String?,
        projection: Array<out String>?,
        queryArgs: Bundle?
    ): Cursor {
        return MatrixCursor(projection).apply {
            val parent = File(parentDocumentId!!)
            parent.listFiles()!!
                .forEach { file ->
                    Log.d("TEST DOCS", file.absolutePath)
                }
        }
    }

    override fun queryDocument(documentId: String?, projection: Array<out String>?): Cursor {
        return MatrixCursor(projection).apply {
            Log.d("TEST DOCS", documentId ?: "None")
        }
    }

    override fun queryChildDocuments(
        parentDocumentId: String?,
        projection: Array<out String>?,
        sortOrder: String?
    ): Cursor {
        return MatrixCursor(projection)
    }

    override fun openDocument(
        documentId: String?,
        mode: String?,
        signal: CancellationSignal?
    ): ParcelFileDescriptor {
        val file = File(documentId!!)
        val accessMode: Int = ParcelFileDescriptor.parseMode(mode)

        val isWrite: Boolean = mode!!.contains("w")
        return if (isWrite) {
            val handler = Handler(requireContext().mainLooper)
            // Attach a close listener if the document is opened in write mode.
            try {
                ParcelFileDescriptor.open(file, accessMode, handler) {
                    // Update the file with the cloud server. The client is done writing.
                    Log.i("TEST DOCS", "A file with id $documentId has been closed! Time to update the server.")
                }
            } catch (e: IOException) {
                throw FileNotFoundException(
                    "Failed to open document with id $documentId and mode $mode"
                )
            }
        } else {
            ParcelFileDescriptor.open(file, accessMode)
        }
    }
}