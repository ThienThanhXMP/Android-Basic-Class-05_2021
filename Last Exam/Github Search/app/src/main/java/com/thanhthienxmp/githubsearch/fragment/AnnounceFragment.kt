package com.thanhthienxmp.githubsearch.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.thanhthienxmp.githubsearch.R
import com.thanhthienxmp.githubsearch.databinding.AnnounceFragmentBinding

class AnnounceFragment : Fragment() {
    // Initialize view binding
    private var bindingRoot: AnnounceFragmentBinding? = null
    private val binding get() = bindingRoot!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingRoot = AnnounceFragmentBinding.inflate(inflater, container, false)
        val announce = arguments?.getString("gitAnnounce") ?: "no_internet"
        val imageAnnounce =
            if (announce == "no_result") R.drawable.no_result else R.drawable.no_internet
        Glide.with(this).load(imageAnnounce).into(binding.announceImage)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        bindingRoot = null
    }
}