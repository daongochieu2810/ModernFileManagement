package com.dnh2810.modernfilemanagement.views

import java.time.LocalDate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager

import com.dnh2810.modernfilemanagement.R
import com.dnh2810.modernfilemanagement.adapters.DirectoryAdapter
import com.dnh2810.modernfilemanagement.databinding.FragmentFileListBinding
import com.dnh2810.modernfilemanagement.files_utils.FileUtils
import com.dnh2810.modernfilemanagement.models.DirectoryModel

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
        val directoryContainer = binding.directoryContainer
        val directories = listOf(
            DirectoryModel(
                "OneDrive",
                R.drawable.onedrive,
                listOf(),
                listOf(),
                LocalDate.now(),
                50f
            )
        )
        val directoryAdapter = DirectoryAdapter(requireContext(), directories)
        val layoutManager =  LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        directoryContainer.layoutManager = layoutManager
        directoryContainer.adapter = directoryAdapter
    }
}