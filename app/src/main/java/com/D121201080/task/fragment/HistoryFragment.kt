package com.D121201080.task.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.D121201080.task.R
import com.D121201080.task.adapter.TaskAdapter
import com.D121201080.task.databinding.FragmentAddBinding
import com.D121201080.task.databinding.FragmentHistoryBinding
import com.D121201080.task.viewmodel.TaskViewModel


class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding?= null
    private val binding get() = _binding!!

    private lateinit var taskViewModel: TaskViewModel
    private val args by navArgs<UpdateFragmentArgs>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHistoryBinding.inflate(layoutInflater,container,false)
        val view = binding.root

        taskViewModel = ViewModelProvider(this)[TaskViewModel::class.java]

        val adapter_task = TaskAdapter()
        binding.recycleTugas.adapter = adapter_task
        binding.recycleTugas.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL,false)
        taskViewModel.readAllDataHistory.observe(viewLifecycleOwner) {task->
            adapter_task.setDataTask(task)
        }

        binding.apply {

        }






        return view
    }



}