package com.example.modernfilemanagement.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.modernfilemanagement.databinding.DirectoryItemCardBinding
import com.example.modernfilemanagement.models.Directory

class DirectoryAdapter(val context: Context, private val directories: List<Directory>): RecyclerView.Adapter<DirectoryAdapter.DirectoryViewHolder>() {

    inner class DirectoryViewHolder(
        private val binding: DirectoryItemCardBinding
        ): RecyclerView.ViewHolder(binding.root) {
            @SuppressLint("UseCompatLoadingForDrawables")
            fun bind(directory: Directory) {
                binding.apply {
                    this.directory = directory
                    image = context.resources.getDrawable(directory.image, context.theme)
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
        val directory = directories[position]
        holder.bind(directory)
    }

    override fun getItemCount(): Int {
        return directories.size
    }
}