package com.dnh2810.modernfilemanagement.views

import android.graphics.Color
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
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
import com.dnh2810.modernfilemanagement.files_utils.MainDocumentsProvider
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

        val provider = MainDocumentsProvider()
        val rootCursor = provider.queryRoots(provider.DEFAULT_ROOT_PROJECTION)
        Log.d("TEST OVERVIEW", "BEFORE")
        while (rootCursor.moveToNext()) {
            Log.d("TEST", "INDIS")
            Log.d("TEST OVERVIEW", rootCursor.getString(rootCursor.getColumnIndex(DocumentsContract.Root.COLUMN_ROOT_ID)))
            provider.queryChildDocuments(rootCursor.getString(rootCursor.getColumnIndex(DocumentsContract.Root.COLUMN_DOCUMENT_ID))
                , provider.DEFAULT_DOCUMENT_PROJECTION, Bundle())
        }

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