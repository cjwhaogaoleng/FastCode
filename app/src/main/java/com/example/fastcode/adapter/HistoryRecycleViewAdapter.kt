package com.example.fastcode.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.fastcode.R
import com.example.fastcode.bean.History
import com.example.fastcode.bean.HistoryBean
import com.example.fastcode.databinding.RecycleViewHistoryItemBinding

class HistoryRecycleViewAdapter(private var data: List<String>, var context: Context): RecyclerView.Adapter<HistoryRecycleViewAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HistoryRecycleViewAdapter.MyViewHolder {
        var inflater = LayoutInflater.from(context)
        var binding =
            DataBindingUtil.inflate<RecycleViewHistoryItemBinding>(inflater, R.layout.recycle_view_history_item, parent, false)
        return MyViewHolder(binding.root)

    }

    override fun onBindViewHolder(holder: HistoryRecycleViewAdapter.MyViewHolder, position: Int) {
        val binding = DataBindingUtil.getBinding<RecycleViewHistoryItemBinding>(holder.itemView)
        binding?.history=History(data[position])
        binding?.executePendingBindings();
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}