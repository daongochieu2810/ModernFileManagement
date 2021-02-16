package com.example.modernfilemanagement.utils

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.modernfilemanagement.R

class OffsetHorizontal(private val context: Context) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val pos = parent.getChildAdapterPosition(view)
        val cardWidth = context.resources.getDimension(R.dimen.storage_card_width)
        val offset = (Const.getScreenWidth() - cardWidth) / 2
        if (pos == 0) {
            outRect.left = offset.toInt()
            outRect.right = context.resources.getDimension(R.dimen.side_margin).toInt()
        }
        if (pos == 1) {
            outRect.right = offset.toInt()
        }
    }

}