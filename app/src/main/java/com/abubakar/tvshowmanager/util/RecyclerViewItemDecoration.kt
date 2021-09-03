package com.abubakar.tvshowmanager.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewItemDecoration(private val topSpace: Int, private val leftSpace: Int,
                                 private val rightSpace: Int, private val bottomSpace: Int)
    : RecyclerView.ItemDecoration() {
    constructor(space: Int) : this(space, space, space, space)

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.top = topSpace
        outRect.left = leftSpace
        outRect.right = rightSpace
        outRect.bottom = bottomSpace
    }
}