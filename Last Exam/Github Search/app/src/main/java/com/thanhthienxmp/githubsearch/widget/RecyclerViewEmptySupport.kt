package com.thanhthienxmp.githubsearch.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.RecyclerView

// When recyclerview have empty, empty layout will replace its space
class RecyclerViewEmptySupport : RecyclerView {
    private var emptyView: View? = null
    private val emptyObserver: AdapterDataObserver = object : AdapterDataObserver() {
        override fun onChanged() {
            val adapter = adapter
            // val itemSize = adapter?.itemCount
            if (adapter != null && emptyView != null) {
                checkEmpty()
//                if (itemSize != null && itemSize > 0)
//                    this@RecyclerViewEmptySupport.smoothScrollToPosition(itemSize - 1)
            }
        }
    }

    constructor(context: Context?) : super(context!!)
    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context!!, attrs, defStyle
    )

    override fun setAdapter(adapter: Adapter<*>?) {
        super.setAdapter(adapter)
        adapter?.registerAdapterDataObserver(emptyObserver)
        emptyObserver.onChanged()
    }

    fun setEmptyView(emptyView: View?) {
        this.emptyView = emptyView
    }

    fun checkEmpty() {
        if (adapter?.itemCount == 0) {
            emptyView!!.visibility = VISIBLE
            this@RecyclerViewEmptySupport.visibility = GONE
        } else {
            emptyView!!.visibility = GONE
            this@RecyclerViewEmptySupport.visibility = VISIBLE
        }
    }
}