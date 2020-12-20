package com.mycodeflow

import android.content.Context
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.RecyclerView


class MovieListItemDecorator(
    private val context: Context,
    private val leftMargin: Int,
    private val bottomMargin: Int,
): RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val metrics = context.resources.displayMetrics
        with(outRect) {
            val position = parent.getChildAdapterPosition(view)
            when{
                position%2 == 0 -> {
                    left = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (leftMargin - 7).toFloat(), metrics).toInt()
                    bottom = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, bottomMargin.toFloat(), metrics).toInt()
                }
                position%2 != 0 -> {
                    left = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (leftMargin - 2).toFloat(), metrics).toInt()
                    bottom = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, bottomMargin.toFloat(), metrics).toInt()
                    right = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftMargin.toFloat(), metrics).toInt()
                }
            }
        }
    }
}