package com.example.modernfilemanagement.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.modernfilemanagement.models.StorageInformation

class StorageOverviewAdapter(private val storageInformationList: List<StorageInformation>) : RecyclerView.Adapter<StorageOverviewAdapter.StorageOverviewViewHolder>() {
    class StorageOverviewViewHolder constructor(itemView: View): RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StorageOverviewViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: StorageOverviewViewHolder, position: Int) {
        val storageInformation: StorageInformation = storageInformationList[position]
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}