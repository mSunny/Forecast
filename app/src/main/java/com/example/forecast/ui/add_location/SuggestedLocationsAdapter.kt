package com.example.forecast.ui.add_location

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.forecast.data.db.Location
import com.example.forecast.databinding.LocationItemBinding
import com.example.forecast.databinding.SuggestedLocationItemBinding

class SuggestedLocationsAdapter(val onItemClickListener:(location: Location)->Unit): RecyclerView.Adapter<ItemViewHolder>() {
    var items = listOf<Location>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.getContext())
        val itemBinding = SuggestedLocationItemBinding.inflate(layoutInflater, parent, false)
        return ItemViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item, onItemClickListener)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var binding: SuggestedLocationItemBinding? = null

    constructor(binding: SuggestedLocationItemBinding): this(binding.root) {
        this.binding = binding
    }

    fun bind(item: Location?,
             onItemClickListener:(location: Location)->Unit) {
        binding?.textView?.text = item?.name
        itemView.setOnClickListener {
            item?.let{onItemClickListener(item)}
        }
    }
}