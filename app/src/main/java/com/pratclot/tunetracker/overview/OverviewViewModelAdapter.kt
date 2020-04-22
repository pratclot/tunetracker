package com.pratclot.tunetracker.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pratclot.tunetracker.database.Tune
import com.pratclot.tunetracker.databinding.ListItemTuneViewBinding

class OverviewViewModelAdapter : ListAdapter<Tune, OverviewViewModelAdapter.ViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class ViewHolder private constructor(val binding: ListItemTuneViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Tune) {
            binding.tune = item
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemTuneViewBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<Tune>() {
    override fun areItemsTheSame(oldItem: Tune, newItem: Tune): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Tune, newItem: Tune): Boolean {
        return areItemsTheSame(oldItem, newItem)
    }
}
