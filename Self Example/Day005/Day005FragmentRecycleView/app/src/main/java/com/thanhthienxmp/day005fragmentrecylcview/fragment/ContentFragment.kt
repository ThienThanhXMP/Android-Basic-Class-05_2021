package com.thanhthienxmp.day005fragmentrecylcview.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.thanhthienxmp.day005fragmentrecylcview.databinding.FragmentContentBinding

class ContentFragment : Fragment() {
    // Initialize view binding
    private var bindingRoot: FragmentContentBinding? = null
    private val binding get() = bindingRoot!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        bindingRoot = FragmentContentBinding.inflate(inflater, container, false)
        // Get current text display on screen
        val currentText = binding.contentData.text.toString()
        // Get data from home fragment
        binding.contentData.text = currentText.plus(arguments?.getString("data_fragment"))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.switchHomeBtn.setOnClickListener { v: View ->
            // Pop the previous fragment (don't change state at all)
            v.findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bindingRoot = null
    }
}