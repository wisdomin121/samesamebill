package com.example.samesamebill

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.samesamebill.databinding.DetailListItemBinding

class DetailAdapter(private val dataSet: ArrayList<ArrayList<ArrayList<String>>>): RecyclerView.Adapter<DetailAdapter.DetailViewHolder>() {
    class DetailViewHolder(val binding: DetailListItemBinding): RecyclerView.ViewHolder(binding.root){}

    override fun getItemCount() = dataSet.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        return DetailViewHolder(DetailListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    fun addItem(item: ArrayList<String>, borrower: ArrayList<String>) {
        dataSet.add(arrayListOf(item, borrower))
        this.notifyItemInserted(dataSet.size-1)
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        val binding = (holder as DetailViewHolder).binding
        val item = dataSet[position][0]
        binding.detailDescription.text = item[0]
        binding.detailCost.text = item[1]
        binding.detailPayer.text = item[2]

        binding.itemRoot.setOnLongClickListener {
            dataSet.removeAt(position)
            this.notifyItemRemoved(position)
            this.notifyItemRangeChanged(position, dataSet.size-position)
            true
        }
    }

    fun getItems(): ArrayList<ArrayList<ArrayList<String>>> {
        return dataSet
    }
}
