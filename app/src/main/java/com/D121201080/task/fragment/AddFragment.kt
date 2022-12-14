package com.D121201080.task.fragment

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.D121201080.task.R
import com.D121201080.task.model.Task
import com.D121201080.task.viewmodel.TaskViewModel
import com.D121201080.task.databinding.FragmentAddBinding
import kotlinx.coroutines.launch


class AddFragment : Fragment() {
    private var _binding:FragmentAddBinding ?= null
    private val binding get() = _binding!!
    private lateinit var taskCategory :ArrayAdapter<CharSequence>
    private  lateinit var taskViewModel: TaskViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentAddBinding.inflate(layoutInflater,container,false)
        val view = binding.root

        taskViewModel = ViewModelProvider(this)[TaskViewModel::class.java]
        (activity as AppCompatActivity).supportActionBar?.title = "Add Task"

        taskCategory = ArrayAdapter.createFromResource(requireContext(),R.array.task_category,android.R.layout.simple_list_item_1)
        binding.dropdownMenu.setAdapter(taskCategory)

        binding.btnSimpan.setOnClickListener {
            val category = binding.dropdownMenu.text.toString()
            val title = binding.taskTitle.text.toString()
            val description = binding.taskDescription.text.toString()

            if (category.isNullOrEmpty()){
                Toast.makeText(requireContext(),"Pilih Kategori",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (title.isNullOrEmpty()){
                binding.taskTitle.error="Judul kosong"
                binding.taskTitle.requestFocus()
                return@setOnClickListener
            }
            if (description.isNullOrEmpty()){
                binding.taskDescription.error="Deskripsi kosong"
                binding.taskDescription.requestFocus()
                return@setOnClickListener
            }

            //add to database
            lifecycleScope.launch{
                val task = Task(0, title,description,category,"Belum Selesai")
                taskViewModel.addTask(task)
                Toast.makeText(requireContext(), "Added Successfully", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_addFragment_to_nav_home)
            }
        }



        return view
    }
}