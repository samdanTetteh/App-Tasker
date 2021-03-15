package com.ijikod.apptasker.presentation

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

abstract class ItemTouchCallback: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

    override fun onMove(recyclerView: RecyclerView, viewHolder:
    RecyclerView.ViewHolder,
                        target: RecyclerView.ViewHolder): Boolean {
        return false
    }

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        val swipeFlag = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        return makeMovementFlags(0, swipeFlag)
    }
}