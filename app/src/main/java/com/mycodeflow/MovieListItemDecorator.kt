package com.mycodeflow

import android.content.Context
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.RecyclerView


class MovieListItemDecorator(
    private val context: Context,
    private val leftMargin: Int,
    private val topMargin: Int,
    private val rightMargin: Int
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
                    left = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftMargin.toFloat(), metrics).toInt()
                }
                1 -> {
                    left = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (leftMargin - 1).toFloat(), metrics).toInt()
                    right = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightMargin.toFloat(), metrics).toInt()
                }
                2 -> {
                    top = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, topMargin.toFloat(), metrics).toInt()
                    left = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftMargin.toFloat(), metrics).toInt()
                }
                3 -> {
                    top = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, topMargin.toFloat(), metrics).toInt()
                    left = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (leftMargin - 1).toFloat(), metrics).toInt()
                    right = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightMargin.toFloat(), metrics).toInt()
                }
            }
        }
    }
}