package com.baturamobile.utils.library

import android.graphics.Rect
import android.view.View

/**
 * Created by vssnake on 14/03/2018.
 */

class ItemOffsetDecoration(private val offset: Int) : androidx.recyclerview.widget.RecyclerView.ItemDecoration() {



    override fun getItemOffsets(outRect: Rect, view: View,
                                parent: androidx.recyclerview.widget.RecyclerView, state: androidx.recyclerview.widget.RecyclerView.State) {
        val bottonIndex: Int
        parent.adapter?.let {
            if (it.itemCount % 2 == 0) {
                bottonIndex = it.itemCount - 2
            } else {
                bottonIndex = it.itemCount - 1
            }

            if (parent.getChildAdapterPosition(view) < bottonIndex) {
                outRect.bottom = offset
            } else {
                outRect.bottom = 0
            }

            if (parent.getChildAdapterPosition(view) > 1) {
                outRect.top = offset
            } else {
                outRect.top = 0
            }

            if (parent.getChildAdapterPosition(view) % 2 == 0) {
                outRect.right = offset
                outRect.left = 0
            } else {
                outRect.right = 0
                outRect.left = offset
            }
        }




    }
}