package com.dnh2810.modernfilemanagement.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.dnh2810.modernfilemanagement.databinding.FileItemCardBinding
import com.dnh2810.modernfilemanagement.models.FileModel
import com.dnh2810.modernfilemanagement.views.FilesListFragment

class FileAdapter(val context: Context, private var fileModels: List<FileModel>):
    RecyclerView.Adapter<FileAdapter.FileViewHolder>() {
    var onClickListener: ((FileModel) -> Unit)? = null
    var onLongClickListener: ((FileModel) -> Unit)? = null

    inner class FileViewHolder(
        private val binding: FileItemCardBinding
        ): RecyclerView.ViewHolder(binding.root), View.OnClickListener, View.OnLongClickListener {
        init {
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }

        override fun onClick(v: View?) {
            onClickListener?.invoke(binding.file!!)
        }

        override fun onLongClick(v: View?): Boolean {
            onLongClickListener?.invoke(binding.file!!)
            return true
        }

        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(fileModel: FileModel) {
            binding.apply {
                file = fileModel
                image = context.resources.getDrawable(fileModel.image, context.theme)
                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
        return FileViewHolder(FileItemCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        val file = fileModels[position]
        holder.bind(file)
    }

    fun updateData(fileModels: List<FileModel>) {
        this.fileModels = fileModels
        notifyDataSetChanged()
    }

    fun setFileClickListener(clickListener: FilesListFragment.OnItemClickListener) {
        onClickListener = {
            clickListener.onClick(it)
        }
        onLongClickListener = {
            clickListener.onLongClick(it)
        }
    }

    override fun getItemCount(): Int {
        return fileModels.size
    }
}