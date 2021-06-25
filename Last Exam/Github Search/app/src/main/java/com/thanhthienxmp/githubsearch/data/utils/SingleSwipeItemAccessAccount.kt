package com.thanhthienxmp.githubsearch.data.utils

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.drawable.ColorDrawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.thanhthienxmp.githubsearch.MainActivity
import com.thanhthienxmp.githubsearch.R
import com.thanhthienxmp.githubsearch.data.adapter.GithubFollowAccountAdapter

object SingleSwipeItemAccessAccount :
    ItemTouchHelper.Callback() {
    private val clearPaint: Paint = Paint()
    private val background: ColorDrawable = ColorDrawable()
    private const val backgroundColor: Int = R.color.design_default_color_primary

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        return makeMovementFlags(0, ItemTouchHelper.RIGHT)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        viewHolder1: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        val itemView = viewHolder.itemView
        val context = itemView.context
        val itemHeight = itemView.height
        val isCancelled = dX == 0f && !isCurrentlyActive
        if (isCancelled) {
            clearCanvas(
                c, itemView.right + dX,
                itemView.top.toFloat(), itemView.right.toFloat(), itemView.bottom.toFloat()
            )
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            return
        }
        background.color = ContextCompat.getColor(context, backgroundColor)
        background.setBounds(
            itemView.left,
            itemView.top,
            itemView.left + dX.toInt(),
            itemView.bottom
        )
        background.draw(c)

        // Icon drawable
        val drawable = ContextCompat.getDrawable(context, R.drawable.ic_search_profile)
        val intrinsicWidth = drawable!!.intrinsicWidth
        val intrinsicHeight = drawable.intrinsicHeight
        val iconTop = itemView.top + (itemHeight - intrinsicHeight) / 2
        val iconMargin = (itemHeight - intrinsicHeight) / 4
        val iconLeft = itemView.left + iconMargin
        val iconRight = itemView.left + iconMargin + intrinsicWidth
        val iconBottom = iconTop + intrinsicHeight
        drawable.setBounds(iconLeft, iconTop, iconRight, iconBottom)
        drawable.draw(c)
        super.onChildDraw(c, recyclerView, viewHolder, dX / 4, dY, actionState, isCurrentlyActive)
    }

    private fun clearCanvas(c: Canvas, left: Float, top: Float, right: Float, bottom: Float) {
        c.drawRect(left, top, right, bottom, clearPaint)
    }

    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        return 0.5f
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val item = viewHolder.itemView
        val context = item.context
        val login = (viewHolder as GithubFollowAccountAdapter.ViewHolder).loginName.text.toString()
        (context as MainActivity).accessAccount(login, init = false, stack = true)
    }

    init {
        clearPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    }
}