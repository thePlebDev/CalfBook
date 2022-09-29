package com.elliottsoftware.calfbook.util

import android.graphics.Canvas
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.elliottsoftware.calfbook.R
import com.elliottsoftware.calfbook.presentation.recyclerViews.FirestoreAdapter

class SwipeToDelete(private val fireStoreAdapter: FirestoreAdapter):
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

       // val calf  = calfListAdapter.currentList[viewHolder.adapterPosition]
        fireStoreAdapter.snapshots.getSnapshot(viewHolder.bindingAdapterPosition).reference.delete()
       // calfViewModel.delete(calf)


        //snackBarCreation.createSnackbarCalfDeleted(Globalview)
    }


    override fun onChildDraw(canvas: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                             dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            val viewItem = viewHolder.itemView
            SwipeBackgroundHelper.paintDrawCommandToStart(canvas, viewItem, R.drawable.ic_trash, dX)
        }
        super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
}