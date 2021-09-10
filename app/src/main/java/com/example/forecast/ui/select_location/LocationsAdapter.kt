package com.example.forecast.ui.select_location

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.forecast.data.db.Location
import com.example.forecast.databinding.LocationItemBinding

class LocationsAdapter(val onItemClickListener:(location: Location)->Unit,
                       val onRemoveClickListener: (itemId: Long)->Unit): RecyclerView.Adapter<ItemViewHolder>() {
    var items = listOf<Location>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.getContext())
        val itemBinding = LocationItemBinding.inflate(layoutInflater, parent, false)
        return ItemViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item, onItemClickListener, onRemoveClickListener)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var binding: LocationItemBinding? = null

    constructor(binding: LocationItemBinding): this(binding.root) {
        this.binding = binding
    }

    fun bind(item: Location?,
             onItemClickListener:(location: Location)->Unit,
             onRemoveClickListener: (itemId: Long)->Unit) {
        binding?.textView?.text = item?.name
        binding?.removeButton?.setOnClickListener {
            item?.id?.let{onRemoveClickListener(it)}
        }
        itemView.setOnClickListener {
            item?.let{onItemClickListener(it)}
        }
    }
}