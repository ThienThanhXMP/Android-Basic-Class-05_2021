package com.thanhthienxmp.day005fragmentrecylcview.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.thanhthienxmp.day005fragmentrecylcview.R
import com.thanhthienxmp.day005fragmentrecylcview.adapter.UserAdapter
import com.thanhthienxmp.day005fragmentrecylcview.databinding.FragmentHome1Binding
import com.thanhthienxmp.day005fragmentrecylcview.databinding.FragmentHome2Binding
import com.thanhthienxmp.day005fragmentrecylcview.databinding.FragmentHomeBinding
import com.thanhthienxmp.day005fragmentrecylcview.model.UserData

class HomeFragment : Fragment() {
    // Initialize view binding
    private var bindingRoot: FragmentHomeBinding? = null
    private val binding get() = bindingRoot!!

    // Define list
    private val userList = mutableListOf<UserData>()

    // Initialize fragment binding
    private lateinit var recycleFragment: FragmentHome1Binding
    private lateinit var dataFormFragment: FragmentHome2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        for (i in 0..5) userList.add(UserData(name = "demo", age = i))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        bindingRoot = FragmentHomeBinding.inflate(inflater, container, false)
        recycleFragment = binding.homeFragment1
        dataFormFragment = binding.homeFragment2

        // Set adapter and empty layout (will show when no item in recyclerview)
        recycleFragment.recycleList.adapter = UserAdapter(userList)
        recycleFragment.recycleList.setEmptyView(binding.root.findViewById(R.id.empty_layout))

        // Check again when fragment re-create
        recycleFragment.recycleList.checkEmpty(
            userList.size,
            recycleFragment.emptyLayout,
            recycleFragment.recycleList
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Switch from home to content
        dataFormFragment.switchContentBtn.setOnClickListener { v: View ->
            v.findNavController()
                .navigate(HomeFragmentDirections.actionHomeFragmentToContentFragment())
        }
        // Add item to recyclerview
        dataFormFragment.addItemBtn.setOnClickListener {
            if (!dataFormFragment.addItemInput.text.isNullOrBlank())
                userList.add(
                    UserData(
                        name = dataFormFragment.addItemInput.text.toString(),
                        age = userList.size
                    )
                )
            recycleFragment.recycleList.adapter?.notifyDataSetChanged()
        }
        // Send data to content fragment
        dataFormFragment.sendDataBtn.setOnClickListener { v: View ->
            if (!dataFormFragment.sendDataInput.text.isNullOrBlank()) {
                // send data as string arguments
                val action =
                    HomeFragmentDirections.actionHomeFragmentToContentFragment(dataFormFragment.sendDataInput.text.toString())
                v.findNavController().navigate(action)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        bindingRoot = null
    }
}