package com.example.monstarlabs_lab5.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.monstarlabs_lab5.CommunicationAdapterWeek
import com.example.monstarlabs_lab5.databinding.ItemWeekBinding
import com.example.monstarlabs_lab5.databinding.ItemWeekCnBinding

class AdapterListWeek(val listener: CommunicationAdapterWeek, var weeks: MutableList<String>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val TYPE_1 = 1
    val TYPE_2 = 2

    class ViewHolderCN(private val binding: ItemWeekCnBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var tv = binding.tvWeek
    }

    class ViewHolder(private val binding: ItemWeekBinding) : RecyclerView.ViewHolder(binding.root) {
        var tv = binding.tvWeek
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_1 -> ViewHolder(
                ItemWeekBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> ViewHolderCN(
                ItemWeekCnBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            TYPE_1 -> {
                var holder: ViewHolder = holder as ViewHolder
                holder.tv.text = weeks[position]
                holder.itemView.setOnClickListener {
                    listener.SortWeek(weeks[position])
                }
            }
            else -> {
                var holder: ViewHolderCN = holder as ViewHolderCN
                holder.tv.text = weeks[position]
                holder.itemView.setOnClickListener {
                    listener.SortWeek(weeks[position])
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return weeks.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (weeks[position]) {
            "Sun" -> TYPE_2
            else -> TYPE_1
        }
    }
}