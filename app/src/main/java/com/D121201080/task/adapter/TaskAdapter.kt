package com.D121201080.task.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.D121201080.task.R
import com.D121201080.task.fragment.HistoryFragmentDirections
import com.D121201080.task.fragment.HomeFragmentDirections
import com.D121201080.task.model.Task

class TaskAdapter:RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
    private var list_task = emptyList<Task>()
    private var context : Context ?= null
    class TaskViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val title :TextView = itemView.findViewById(R.id.judul_tugas)
        val description :TextView = itemView.findViewById(R.id.isi_tugas)
        val category :TextView = itemView.findViewById(R.id.kategori_tugas)
        val update :CardView = itemView.findViewById(R.id.rowLayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        context = parent.context
        return TaskViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.adapter_task,parent,false))
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val currentTask = list_task[position]
        holder.title.text = currentTask.judul
        holder.description.text = currentTask.isi
        holder.category.text = currentTask.kategori



        if (currentTask.status=="Selesai"){
            holder.update.setOnClickListener{
                val action = HistoryFragmentDirections.actionNavHistoryToNavUpdate(currentTask)
                holder.itemView.findNavController().navigate(action)
            }
        }else{
            holder.update.setOnClickListener{
                val action = HomeFragmentDirections.actionNavHomeToUpdateFragment(currentTask)
                holder.itemView.findNavController().navigate(action)
            }
        }


        when(currentTask.kategori){
            "Penting Mendesak"->{
                holder.category.backgroundTintList = ColorStateList.valueOf(context!!.resources.getColor(R.color.warnaMerah))
            }
            "Tidak Penting Mendesak"->{
                holder.category.backgroundTintList = ColorStateList.valueOf(context!!.resources.getColor(R.color.warnaOrens))
            }
            "Penting Tidak Mendesak"->{
                holder.category.backgroundTintList = ColorStateList.valueOf(context!!.resources.getColor(R.color.warnaKuning))
            }
            "Tidak Penting Tidak Mendesak"->{
                holder.category.backgroundTintList = ColorStateList.valueOf(context!!.resources.getColor(R.color.warnaHijau))
            }
        }
    }

    override fun getItemCount(): Int {
        return list_task.size
    }
    fun setDataTask(task:List<Task>){
        this.list_task = task
        notifyDataSetChanged()
    }
}