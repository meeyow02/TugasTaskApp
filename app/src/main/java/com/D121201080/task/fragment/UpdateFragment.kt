package com.D121201080.task.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.text.InputType
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.D121201080.task.R
import com.D121201080.task.databinding.FragmentUpdateBinding
import com.D121201080.task.model.Task
import com.D121201080.task.viewmodel.TaskViewModel
import kotlinx.coroutines.launch


class UpdateFragment : Fragment() {

    private var _binding: FragmentUpdateBinding?= null
    private val binding get() = _binding!!

    private val args by navArgs<UpdateFragmentArgs>()

    private lateinit var taskViewModel: TaskViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentUpdateBinding.inflate(layoutInflater,container,false)
        val view = binding.root

        taskViewModel = ViewModelProvider(this)[TaskViewModel::class.java]
        (activity as AppCompatActivity).supportActionBar?.title = "Detail"

        binding!!.apply {
            if(args.currentTask.status=="Selesai"){
                updropdownMenu.setText(args.currentTask.kategori)
                uptaskTitle.setText(args.currentTask.judul)
                upddescription.setText(args.currentTask.isi)
                binding.uptaskTitle.inputType= InputType.TYPE_NULL
                binding.upddescription.inputType= InputType.TYPE_NULL
                binding.btnUpdate.visibility = View.GONE
                binding.btnSelesai.visibility = View.GONE


            }else{
                updropdownMenu.setText(args.currentTask.kategori)
                updropdownMenu.setAdapter(ArrayAdapter.createFromResource(requireContext(),R.array.task_category, android.R.layout.simple_list_item_1))
                uptaskTitle.setText(args.currentTask.judul)
                upddescription.setText(args.currentTask.isi)
                btnUpdate.setOnClickListener {
                    updateItem()
                }
                btnSelesai.setOnClickListener {
                    finishItem()
                }
            }


            //add menu
            setHasOptionsMenu(true)
        }

        return view
    }

    private fun finishItem(){

        binding.apply setOnClickListener@{
            val category = binding.updropdownMenu.text.toString()
            val title = binding.uptaskTitle.text.toString()
            val description = binding.upddescription.text.toString()

            if (category.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "Pilih Kategori", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (title.isNullOrEmpty()) {
                binding.uptaskTitle.error = "Judul kosong"
                binding.uptaskTitle.requestFocus()
                return@setOnClickListener
            }
            if (description.isNullOrEmpty()) {
                binding.upddescription.error = "Deskripsi kosong"
                binding.upddescription.requestFocus()
                return@setOnClickListener
            }

            //add to database
            lifecycleScope.launch {
                val updatedTask = Task(args.currentTask.id, title, description, category, "Selesai")
                taskViewModel.updateTask(updatedTask)
                Toast.makeText(requireContext(), "Finish Task", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_updateFragment_to_nav_home)

            }
        }
    }

    private fun updateItem(){

        binding.apply setOnClickListener@{
            val category = binding.updropdownMenu.text.toString()
            val title = binding.uptaskTitle.text.toString()
            val description = binding.upddescription.text.toString()

            if (category.isNullOrEmpty()){
                Toast.makeText(requireContext(),"Pilih Kategori",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (title.isNullOrEmpty()){
                binding.uptaskTitle.error="Judul kosong"
                binding.uptaskTitle.requestFocus()
                return@setOnClickListener
            }
            if (description.isNullOrEmpty()){
                binding.upddescription.error="Deskripsi kosong"
                binding.upddescription.requestFocus()
                return@setOnClickListener
            }

            //add to database
            lifecycleScope.launch{
                val updatedTask = Task(args.currentTask.id, title,description,category,"Belum Selesai")
                taskViewModel.updateTask(updatedTask)
                Toast.makeText(requireContext(), "Updated Successfully", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_updateFragment_to_nav_home)

            }

        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete){
            deleteTask()
        }
        return super.onOptionsItemSelected(item)
    }


    private fun deleteTask() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton(("Yes")) { _, _ ->
            taskViewModel.deleteTask(args.currentTask)
            Toast.makeText(requireContext(), "Successfully removed: ${args.currentTask.judul}", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_nav_home)
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete ${args.currentTask.judul}?")
        builder.setMessage("Yakin ingin menghapus ${args.currentTask.judul}?")
        builder.create().show()
    }
}