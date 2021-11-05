package com.sillyapps.alarmarmy.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sillyapps.alarmarmy.data.Alarm
import com.sillyapps.alarmarmy.databinding.ItemAlarmBinding
import com.sillyapps.alarmarmy.ui.ListClickListener

class AlarmAdapter(private val onClickListener: ListClickListener): ListAdapter<Alarm, AlarmAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onClickListener)
    }

    class ViewHolder private constructor(private val binding: ItemAlarmBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Alarm, onClickListener: ListClickListener) {
            binding.alarm = item
            binding.root.setOnClickListener { onClickListener.onClick(bindingAdapterPosition) }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)

                val binding = ItemAlarmBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    class DiffCallback: DiffUtil.ItemCallback<Alarm>() {
        override fun areItemsTheSame(oldItem: Alarm, newItem: Alarm): Boolean {
            return oldItem.id == oldItem.id
        }

        override fun areContentsTheSame(oldItem: Alarm, newItem: Alarm): Boolean {
            return oldItem == newItem
        }

    }
}