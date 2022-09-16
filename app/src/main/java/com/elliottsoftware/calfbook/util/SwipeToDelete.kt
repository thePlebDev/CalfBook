package com.elliottsoftware.calfbook.util

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.elliottsoftware.calfbook.recyclerViews.CalfListAdapter
import com.elliottsoftware.calfbook.viewModles.CalfViewModel

class SwipeToDelete(private val calfViewModel: CalfViewModel, private val calfListAdapter: CalfListAdapter):
    ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        //NOT BEING IMPLEMENTED
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

        val calf  = calfListAdapter.currentList[viewHolder.adapterPosition]
        calfViewModel.delete(calf)


        //snackBarCreation.createSnackbarCalfDeleted(Globalview)
    }
}