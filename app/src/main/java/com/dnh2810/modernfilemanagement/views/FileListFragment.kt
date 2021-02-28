package com.dnh2810.modernfilemanagement.views

import java.time.Instant

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager

import com.dnh2810.modernfilemanagement.R
import com.dnh2810.modernfilemanagement.adapters.FileAdapter
import com.dnh2810.modernfilemanagement.databinding.FragmentFileListBinding
import com.dnh2810.modernfilemanagement.files_utils.FileUtils
import com.dnh2810.modernfilemanagement.files_utils.FileUtils.getFileModelsFromFiles
import com.dnh2810.modernfilemanagement.files_utils.FileUtils.getFilesFromPath
import com.dnh2810.modernfilemanagement.models.FileModel
import com.dnh2810.modernfilemanagement.models.FileType

class FilesListFragment : Fragment() {
    private lateinit var binding: FragmentFileListBinding
    private lateinit var path: String

    companion object {
        private const val ARG_PATH: String = "com.dnh2810.modernfilemanagement"
        fun build(block: Builder.() -> Unit) = Builder().apply(block).build()
    }

    class Builder {
        var path: String = ""

        fun build(): FilesListFragment {
            val fragment = FilesListFragment()
            val args = Bundle()
            args.putString(ARG_PATH, path)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_file_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFileListBinding.bind(view)

        val filePath = arguments?.getString(ARG_PATH)
        if (filePath == null) {
            Toast.makeText(context, "Path should not be null!", Toast.LENGTH_SHORT).show()
            return
        }
        path = filePath

        populateDirectories()
    }

    private fun retrieveFilesFromPath() {
        val fileList = FileUtils.getFilesFromPath(path)
        val fileModelList = FileUtils.getFileModelsFromFiles(fileList)

    }

    private fun populateDirectories() {
        val fileContainer = binding.fileContainer
        val files = getFileModelsFromFiles(getFilesFromPath(path))
        Log.d("FileListFragment", files.size.toString())

        val fileAdapter = FileAdapter(requireContext(), files)
        val layoutManager =  LinearLayoutManager(requireContext())

        layoutManager.orientation = LinearLayoutManager.VERTICAL
        fileContainer.layoutManager = layoutManager
        fileContainer.adapter = fileAdapter
    }
}