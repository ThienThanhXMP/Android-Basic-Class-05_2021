package com.thanhthienxmp.day005fragmentrecylcview.widget

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
            val itemSize = adapter?.itemCount
            if (adapter != null && emptyView != null) {
                checkEmpty(
                    itemSize!!,
                    emptyView,
                    this@RecyclerViewEmptySupport
                )
                if (itemSize > 0)
                    this@RecyclerViewEmptySupport.smoothScrollToPosition(itemSize - 1)
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

    fun checkEmpty(dataSize: Int, emptyView: View?, recyclerView: View?) {
        if (dataSize == 0) {
            emptyView!!.visibility = VISIBLE
            recyclerView!!.visibility = GONE
        } else {
            emptyView!!.visibility = GONE
            recyclerView!!.visibility = VISIBLE
        }
    }
}