package com.thanhthienxmp.day005fragmentrecylcview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.thanhthienxmp.day005fragmentrecylcview.R
import com.thanhthienxmp.day005fragmentrecylcview.model.UserData

class UserAdapter(private val list: MutableList<UserData>) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    override fun getItemCount(): Int = list.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.text_item)
        val deleteBtn: ImageButton = view.findViewById(R.id.delete_btn)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val name = list[position].name
        val age = list[position].age
        // Define text view display
        holder.textView.text = name.plus(" - $age${checkLevel(age)}")
        // Button will remove item out recyclerview - show snack bar instance
        holder.deleteBtn.setOnClickListener {
            list.removeAt(position)
            val snack = Snackbar.make(
                it.rootView.findViewById(R.id.main_activity),
                "You just deleted ${holder.textView.text}",
                Snackbar.LENGTH_SHORT
            )
            snack.setAction("DISMISS") {}
            snack.show()
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycle_item, parent, false)
        return ViewHolder(view)
    }

    // Function change order in list items
    private fun checkLevel(level: Int): String =
        when (level.toString().last()) {
            '1' -> "st"
            '2' -> "nd"
            '3' -> "rd"
            else -> "th"
        }
}
