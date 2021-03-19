package com.dnh2810.modernfilemanagement.views

import android.graphics.Color
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView

import com.dnh2810.modernfilemanagement.R
import com.dnh2810.modernfilemanagement.adapters.StorageOverviewAdapter
import com.dnh2810.modernfilemanagement.databinding.FragmentOverviewBinding
import com.dnh2810.modernfilemanagement.models.StorageInformation
import com.dnh2810.modernfilemanagement.utils.OffsetHorizontal
import com.dnh2810.modernfilemanagement.utils.ProminentLayoutManager
import java.io.File

class OverviewFragment : Fragment() {
    private var binding: FragmentOverviewBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_overview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentOverviewBinding.bind(view)
        setUpClickListeners()

        val file = File(requireContext().getExternalFilesDir(null)!!.absolutePath)
        Log.d("TEST", file.absolutePath)
        val file2 = File(Environment.getExternalStorageDirectory().absolutePath)
        Log.d("TEST", if (file2.listFiles() == null) "0" else file2.listFiles().size.toString())
        val file3 = File(requireContext().getExternalFilesDir(null)!!.absolutePath)
        Log.d("TEST", file3.absolutePath)
        val file4 = File(Environment.getStorageDirectory().absolutePath)
        Log.d("TEST", file4.absolutePath)
        val file5 = File(Environment.getDataDirectory().absolutePath)
        Log.d("TEST", file5.absolutePath)
        val file6 = File(Environment.getDownloadCacheDirectory().absolutePath)
        Log.d("TEST", file6.absolutePath)
        val file7 = File(Environment.DIRECTORY_DOWNLOADS)
        Log.d("TEST", file7.absolutePath)
        val file8 = File(Environment.getRootDirectory().absolutePath)
        Log.d("TEST", file8.absolutePath)

        val storageCardsContainer: RecyclerView = binding!!.storageCardsContainer
        val storageInformationList: List<StorageInformation> = listOf(
            StorageInformation(.45f, 1f, 700, StorageInformation.StorageType.INTERNAL),
            StorageInformation(.45f, 1f, 1000, StorageInformation.StorageType.EXTERNAL)
        )
        val storageOverviewAdapter = StorageOverviewAdapter(requireContext(), storageInformationList)
        val layoutManager = ProminentLayoutManager(requireContext())
        val decoration = OffsetHorizontal(requireContext())

        storageCardsContainer.layoutManager = layoutManager
        storageCardsContainer.adapter = storageOverviewAdapter
        storageCardsContainer.addItemDecoration(decoration)
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(storageCardsContainer)

    }

    private fun setUpClickListeners() {
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

}