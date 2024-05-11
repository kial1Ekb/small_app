package com.example.shoppinglist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ShoppingListAdapter(private val onItemAction: (ShoppingItem, String) -> Unit) :
    RecyclerView.Adapter<ShoppingListAdapter.ViewHolder>() {

    private var items: MutableList<ShoppingItem> = mutableListOf()

    fun addItem(item: ShoppingItem) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun editItem(item: ShoppingItem) {
        val index = items.indexOf(item)
        items[index] = item
        notifyItemChanged(index)

    }

    fun deleteItem(item: ShoppingItem) {
        val index = items.indexOf(item)
        if (index != -1) {
            items.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_shopping, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.itemNameTextView)
        private val editButton: ImageButton = itemView.findViewById(R.id.editButton)
        private val deleteButton: ImageButton = itemView.findViewById(R.id.deleteButton)

        fun bind(item: ShoppingItem) {
            nameTextView.text = item.value
            editButton.setOnClickListener { onItemAction(item, "edit") }
            deleteButton.setOnClickListener { onItemAction(item, "delete") }
        }
    }
}