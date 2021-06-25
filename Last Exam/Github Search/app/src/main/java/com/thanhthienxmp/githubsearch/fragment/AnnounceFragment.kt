package com.thanhthienxmp.githubsearch.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.thanhthienxmp.githubsearch.databinding.AnnounceFragmentBinding

class AnnounceFragment: Fragment() {
    // Initialize view binding
    private var bindingRoot: AnnounceFragmentBinding? = null
    private val binding get() = bindingRoot!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingRoot = AnnounceFragmentBinding.inflate(inflater, container, false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        bindingRoot = null
    }
}