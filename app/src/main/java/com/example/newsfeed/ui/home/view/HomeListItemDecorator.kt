package com.example.newsfeed.ui.home.view

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class HomeListItemDecorator(private val spacing: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val count = parent.adapter?.itemCount

        outRect.left = spacing
        outRect.right = spacing

        if (position == 0)
            outRect.top = spacing

        if (position == count?.minus(1))
            outRect.bottom = spacing
    }
}