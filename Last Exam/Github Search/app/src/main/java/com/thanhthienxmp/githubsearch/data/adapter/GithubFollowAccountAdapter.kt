package com.thanhthienxmp.githubsearch.data.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.thanhthienxmp.githubsearch.MainActivity
import com.thanhthienxmp.githubsearch.R
import com.thanhthienxmp.githubsearch.data.model.GitFollow
import com.thanhthienxmp.githubsearch.data.utils.GlideApp

abstract class GithubFollowAccountAdapter(
    private val list: GitFollow,
    private var reverse: Boolean
) :
    RecyclerView.Adapter<GithubFollowAccountAdapter.ViewHolder>() {
    private var lastPosition = -1

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val avatar: ImageView = view.findViewById(R.id.follow_avatar)
        val loginName: TextView = view.findViewById(R.id.follow_login)
        val layout: View = view.findViewById(R.id.follow_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.profile_fragment_follow_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.itemView.context
        val formatList = if (reverse) list.reversed() else list
        val image = formatList[position].avatar
        val name = formatList[position].followLogin
        if (position % 2 != 0) holder.layout.setBackgroundColor(Color.parseColor("#eeffee"))
        else holder.layout.setBackgroundColor(Color.parseColor("#ffffff"))
        // Show image
        GlideApp.with(context).load(image).into(holder.avatar)
        holder.loginName.text = name
        // Set animation
        if (holder.adapterPosition > lastPosition) {
            val animation = AnimationUtils.loadAnimation(context, R.anim.slide_row_item)
            animation.duration = 250
            holder.itemView.startAnimation(animation)
            lastPosition = holder.adapterPosition
        }
        holder.itemView.setOnLongClickListener {
            val activity = (context as MainActivity)
            if (activity.isStacked) {
                Toast.makeText(activity, "Can\'t interact in stacked player", Toast.LENGTH_SHORT)
                    .show()
            }
            true
        }
    }

    override fun getItemCount(): Int = list.size
}