package com.app.sbnri.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ItemDecorator(
    private val mLeft: Int,
    private val mRight: Int,
    private val mTop: Int,
    private val mBottom: Int
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.apply {
            left = mLeft
            right = mRight
            top = mTop
            bottom = mBottom
        }
    }
}