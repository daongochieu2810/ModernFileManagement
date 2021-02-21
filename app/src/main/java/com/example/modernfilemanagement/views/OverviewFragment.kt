package com.example.modernfilemanagement.views

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.modernfilemanagement.R
import com.example.modernfilemanagement.adapters.StorageOverviewAdapter
import com.example.modernfilemanagement.databinding.FragmentOverviewBinding
import com.example.modernfilemanagement.models.StorageInformation
import com.example.modernfilemanagement.utils.OffsetHorizontal
import com.example.modernfilemanagement.utils.ProminentLayoutManager

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
        val internalTextView = binding!!.internalStorageTitle
        val externalTextView = binding!!.externalStorageTitle

        setUpClickListeners()

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

        storageCardsContainer.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val id = (recyclerView.layoutManager as ProminentLayoutManager).currentCardId
                if (id == 0) {
                    internalTextView.setTextColor(Color.YELLOW)
                    externalTextView.setTextColor(Color.WHITE)
                } else {
                    internalTextView.setTextColor(Color.WHITE)
                    externalTextView.setTextColor(Color.YELLOW)
                }
            }
        })

    }

    private fun setUpClickListeners() {
        val internalTextView = binding!!.internalStorageTitle
        val externalTextView = binding!!.externalStorageTitle
        val storageCardsContainer = binding!!.storageCardsContainer

        val storageTypeChangeListener = object: View.OnClickListener {
            override fun onClick(v: View?) {
                if ((v as TextView).text == StorageInformation.StorageType.INTERNAL.displayText) {
                    internalTextView.setTextColor(Color.YELLOW)
                    externalTextView.setTextColor(Color.WHITE)
                    storageCardsContainer.smoothScrollToPosition(0)

                } else {
                    internalTextView.setTextColor(Color.WHITE)
                    externalTextView.setTextColor(Color.YELLOW)
                    storageCardsContainer.smoothScrollToPosition(1)

                }
            }
        }
        binding!!.apply {
            onTitleClick = storageTypeChangeListener
            executePendingBindings()
        }
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

}