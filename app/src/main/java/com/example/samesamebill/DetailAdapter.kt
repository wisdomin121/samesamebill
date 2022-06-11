package com.example.samesamebill

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.samesamebill.databinding.DetailListItemBinding

class DetailAdapter(private val dataSet: ArrayList<List<Any>>): RecyclerView.Adapter<DetailAdapter.DetailViewHolder>() {
    class DetailViewHolder(val binding: DetailListItemBinding): RecyclerView.ViewHolder(binding.root){}

    override fun getItemCount() = dataSet.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        return DetailViewHolder(DetailListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    fun addItem(item: List<Any>) {
        dataSet.add(item)
        this.notifyItemInserted(dataSet.size-1)
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        val binding = (holder as DetailViewHolder).binding
        binding.detailDescription.text = dataSet[position][0] as CharSequence?
        binding.detailCost.text = dataSet[position][1] as CharSequence?
        binding.detailPayer.text = dataSet[position][2] as CharSequence?

        binding.itemRoot.setOnLongClickListener {
            dataSet.removeAt(position)
            this.notifyItemRemoved(position)
            this.notifyItemRangeChanged(position, dataSet.size-position)
            true
        }
    }
}