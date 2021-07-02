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
import com.thanhthienxmp.githubsearch.data.model.GitFollowAccount
import com.thanhthienxmp.githubsearch.data.utils.GlideApp
import com.thanhthienxmp.githubsearch.databinding.ProfileFragmentFollowItemBinding

abstract class GithubFollowAccountAdapter(
    private val list: MutableList<GitFollowAccount>,
    private var reverse: Boolean
) :
    RecyclerView.Adapter<GithubFollowAccountAdapter.ViewHolder>() {
    private var lastPosition = -1
    private var binding: ProfileFragmentFollowItemBinding? = null

    class ViewHolder(view: ProfileFragmentFollowItemBinding) : RecyclerView.ViewHolder(view.root) {
        val avatar: ImageView = view.followAvatar
        val loginName: TextView = view.followLogin
        val layout: View = view.followItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding =
            ProfileFragmentFollowItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.itemView.context
        val formatList = if (reverse) list.reversed() else list
        val git = formatList[position]
        val image = git.avatar
        val name = if (git.isFollower) git.git else git.git.split("#F").first()
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
        holder.itemView.setOnClickListener {
            val activity = (context as MainActivity)
            if (activity.isStacked) {
                Toast.makeText(activity, "Can\'t interact in stacked player", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        binding = null
    }

    override fun getItemCount(): Int = list.size
}