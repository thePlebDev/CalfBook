package com.elliottsoftware.calfbook.util

import android.annotation.SuppressLint
import android.graphics.*
import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.elliottsoftware.calfbook.R

class SwipeBackgroundHelper {

    companion object {

        private const val THRESHOLD = 5.5

        private const val OFFSET_PX = 20

        @JvmStatic
        fun paintDrawCommandToStart(
            canvas: Canvas, //recyclerView canvas
            viewItem: View, // the viewItem being swiped
            @DrawableRes iconResId: Int, //trashcan icon
            dX: Float // the change in x
        ) {
            val drawCommand = createDrawCommand(viewItem, dX, iconResId) // gets the icon and sets the colors
            paintDrawCommand(drawCommand, canvas, dX, viewItem)
        }

        @SuppressLint("RestrictedApi")
        private fun createDrawCommand(viewItem: View, dX: Float, iconResId: Int): DrawCommand {
            val context = viewItem.context
            var icon = ContextCompat.getDrawable(context, iconResId)!! //the trash icon Drawable
            icon = DrawableCompat.wrap(icon).mutate() //making the Drawable mutable
            icon.colorFilter = PorterDuffColorFilter(
                ContextCompat.getColor(context, R.color.white),
                PorterDuff.Mode.SRC_IN)// setting the color
            val backgroundColor = getBackgroundColor(R.color.red, R.color.white, dX, viewItem)
            return DrawCommand(icon, backgroundColor)
        }
        private fun getBackgroundColor(firstColor: Int, secondColor: Int, dX: Float, viewItem: View): Int {
            return when (willActionBeTriggered(dX, viewItem.width)) {
                true -> ContextCompat.getColor(viewItem.context, firstColor) //red color
                false -> ContextCompat.getColor(viewItem.context, secondColor) // normal white color
            }
        }
        private fun willActionBeTriggered(dX: Float, viewWidth: Int): Boolean {
            return Math.abs(dX) >= viewWidth / THRESHOLD // when the red will appear
        }
        private fun paintDrawCommand(drawCommand: DrawCommand, canvas: Canvas, dX: Float, viewItem: View) {
            drawBackground(canvas, viewItem, dX, drawCommand.backgroundColor)
            drawIcon(canvas, viewItem, dX, drawCommand.icon)
        }

        private fun drawIcon(canvas: Canvas, viewItem: View, dX: Float, icon: Drawable) {
            val topMargin = calculateTopMargin(icon, viewItem)
            icon.bounds = getStartContainerRectangle(viewItem, icon.intrinsicWidth, topMargin, OFFSET_PX, dX)
            icon.draw(canvas)
        }
        private fun getStartContainerRectangle(viewItem: View, iconWidth: Int, topMargin: Int, sideOffset: Int,
                                               dx: Float): Rect {
            val leftBound = viewItem.right + dx.toInt() + sideOffset
            val rightBound = viewItem.right + dx.toInt() + iconWidth + sideOffset
            val topBound = viewItem.top + topMargin
            val bottomBound = viewItem.bottom - topMargin

            return Rect(leftBound, topBound, rightBound, bottomBound) //drawing the icon dimensions
        }

        private fun calculateTopMargin(icon: Drawable, viewItem: View): Int {
            return (viewItem.height - icon.intrinsicHeight) / 2
        }
        private fun drawBackground(canvas: Canvas, viewItem: View, dX: Float, color: Int) {
            val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG)
            backgroundPaint.color = color
            val backgroundRectangle = getBackGroundRectangle(viewItem, dX)
            canvas.drawRect(backgroundRectangle, backgroundPaint)
        }

        private fun getBackGroundRectangle(viewItem: View, dX: Float): RectF {
            return RectF(viewItem.right.toFloat() + dX, viewItem.top.toFloat(), viewItem.right.toFloat(),
                viewItem.bottom.toFloat())
        }


    }



    //used to handle and encapsulate resources
    private class DrawCommand internal constructor(internal val icon: Drawable, internal val backgroundColor: Int)
}