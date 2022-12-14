package com.D121201080.task.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.D121201080.task.R
import com.D121201080.task.adapter.TaskAdapter
import com.D121201080.task.viewmodel.TaskViewModel
import com.D121201080.task.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding:FragmentHomeBinding ?= null
    private val binding get() = _binding!!
    private  lateinit var taskViewModel: TaskViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater,container,false)
        val view = binding.root
        taskViewModel = ViewModelProvider(this)[TaskViewModel::class.java]
        binding.floatingActionButton.setOnClickListener{
            findNavController().navigate(R.id.action_nav_home_to_addFragment)
        }

        val adapter_task = TaskAdapter()
        binding.recycleTugas.adapter = adapter_task
        binding.recycleTugas.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        taskViewModel.readAllDataHome.observe(viewLifecycleOwner) {task->
            adapter_task.setDataTask(task)
        }

        return view
    }

}