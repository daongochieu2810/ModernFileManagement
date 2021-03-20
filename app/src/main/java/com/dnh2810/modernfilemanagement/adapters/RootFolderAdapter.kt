package com.dnh2810.modernfilemanagement.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dnh2810.modernfilemanagement.databinding.RootFolderItemCardBinding
import com.dnh2810.modernfilemanagement.models.RootFolder

class RootFolderAdapter (
    val context: Context, val rootFolders: List<RootFolder>
): RecyclerView.Adapter<RootFolderAdapter.RootFolderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RootFolderViewHolder {
        return RootFolderViewHolder(
            RootFolderItemCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false)
        )
    }

    override fun onBindViewHolder(holder: RootFolderViewHolder, position: Int) {
    }

    override fun getItemCount(): Int {
        return rootFolders.size
    }

    inner class RootFolderViewHolder(val binding: RootFolderItemCardBinding): RecyclerView.ViewHolder(binding.root) {

    }
}