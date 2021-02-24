package com.dnh2810.modernfilemanagement.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dnh2810.modernfilemanagement.databinding.DirectoryItemCardBinding
import com.dnh2810.modernfilemanagement.models.DirectoryModel

class DirectoryAdapter(val context: Context, private val directoryModels: List<DirectoryModel>): RecyclerView.Adapter<DirectoryAdapter.DirectoryViewHolder>() {

    inner class DirectoryViewHolder(
        private val binding: DirectoryItemCardBinding
        ): RecyclerView.ViewHolder(binding.root) {
            @SuppressLint("UseCompatLoadingForDrawables")
            fun bind(directoryModel: DirectoryModel) {
                binding.apply {
                    this.directory = directoryModel
                    image = context.resources.getDrawable(directoryModel.image, context.theme)
                    executePendingBindings()
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DirectoryViewHolder {
        return DirectoryViewHolder(DirectoryItemCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: DirectoryViewHolder, position: Int) {
        val directory = directoryModels[position]
        holder.bind(directory)
    }

    override fun getItemCount(): Int {
        return directoryModels.size
    }
}