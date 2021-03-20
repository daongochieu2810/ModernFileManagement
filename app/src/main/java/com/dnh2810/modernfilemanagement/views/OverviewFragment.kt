package com.dnh2810.modernfilemanagement.views

import android.content.pm.ProviderInfo
import android.os.Bundle
import android.provider.DocumentsContract
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView

import com.dnh2810.modernfilemanagement.R
import com.dnh2810.modernfilemanagement.adapters.RootFolderAdapter
import com.dnh2810.modernfilemanagement.databinding.FragmentOverviewBinding
import com.dnh2810.modernfilemanagement.files_utils.MainDocumentsProvider
import com.dnh2810.modernfilemanagement.models.RootFolder
import java.time.LocalDate

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

        while (rootCursor.moveToNext()) {
            Log.d("TEST", "INDIS")
            Log.d("TEST OVERVIEW", rootCursor.getString(rootCursor.getColumnIndex(DocumentsContract.Root.COLUMN_ROOT_ID)))
            provider.queryChildDocuments(rootCursor.getString(rootCursor.getColumnIndex(DocumentsContract.Root.COLUMN_ROOT_ID))
                , provider.DEFAULT_DOCUMENT_PROJECTION, Bundle())
        }

        val storageCardsContainer: RecyclerView = binding!!.storageCardsContainer
        val layoutManager = GridLayoutManager(requireContext(), 2)
        val rootFolderAdapter = RootFolderAdapter(requireContext(),
            listOf(RootFolder("hello", LocalDate.now(), 0),
                RootFolder("hello", LocalDate.now(), 0),
                RootFolder("hello", LocalDate.now(), 0),
                RootFolder("hello", LocalDate.now(), 0),
                RootFolder("hello", LocalDate.now(), 0)
            ))

        storageCardsContainer.layoutManager = layoutManager
        storageCardsContainer.adapter = rootFolderAdapter
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