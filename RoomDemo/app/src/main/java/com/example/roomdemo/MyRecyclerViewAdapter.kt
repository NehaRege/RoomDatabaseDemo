package com.example.roomdemo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdemo.databinding.ListItemBinding
import com.example.roomdemo.db.Subscriber

class MyRecyclerViewAdapter(
    private val subscribers: List<Subscriber>
): RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ListItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.list_item, parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return subscribers.size
    }

    // This is to display data on the list item
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(subscribers[position])
    }
}

class MyViewHolder(
    val binding: ListItemBinding
): RecyclerView.ViewHolder(binding.root) {

    fun bind(subscriber: Subscriber) {
        binding.emailTextview.text = subscriber.email
        binding.nameTextview.text = subscriber.name
    }

}