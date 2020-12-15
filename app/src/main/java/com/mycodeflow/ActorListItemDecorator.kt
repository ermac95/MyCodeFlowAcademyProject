package com.mycodeflow

import android.content.Context
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ActorListItemDecorator(
    private val context: Context,
    private val margin: Int
): RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val metrics = context.resources.displayMetrics
        with(outRect) {
            when(parent.getChildAdapterPosition(view)){
                0 -> {
                    left = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, margin.toFloat(), metrics).toInt()
                    right = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (margin - 8).toFloat(), metrics).toInt()
                }
                1, 2 -> {
                    right = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (margin - 8).toFloat(), metrics).toInt()
                }
                3 -> {
                    right = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, margin.toFloat(), metrics).toInt()
                }
            }
        }
    }
}