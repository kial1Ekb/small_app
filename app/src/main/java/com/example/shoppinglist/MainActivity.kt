package com.example.shoppinglist

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: ShoppingListAdapter

    private lateinit var newItemEditText: EditText
    private lateinit var addItemButton: Button
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        newItemEditText = findViewById(R.id.newItemEditText)
        addItemButton = findViewById(R.id.addItemButton)
        recyclerView = findViewById(R.id.recyclerView)

        addItemButton.setOnClickListener {
            val text = newItemEditText.text.toString().trim()
            if (text.isNotEmpty()) {
                addItem(ShoppingItem(text))
                newItemEditText.setText("")
                newItemEditText.isFocusable = true
            } else Toast.makeText(this, "Введите текст", Toast.LENGTH_LONG).show()
        }


        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ShoppingListAdapter { item, action ->
            when (action) {
                "edit" -> editItem(item)
                "delete" -> deleteItem(item)
            }
        }
        recyclerView.adapter = adapter
    }

    private fun addItem(item: ShoppingItem) {
        adapter.addItem(item)
    }

    private fun editItem(item: ShoppingItem) {
        val editText = EditText(this).apply { setText(item.value) }
        AlertDialog.Builder(this)
            .setTitle("Редактировать пункт")
            .setView(editText)
            .setPositiveButton("Сохранить") { dialog, _ ->
                val newName = editText.text.toString()
                item.value = newName
                adapter.editItem(item)
                dialog.dismiss()
            }
            .setNegativeButton("Отмена") { dialog, _ ->
                dialog.cancel()
            }
            .create()
            .show()
    }

    private fun deleteItem(item: ShoppingItem) {
        AlertDialog.Builder(this)
            .setTitle("Подтверждение удаления")
            .setMessage("Вы уверены, что хотите удалить ${item.value}?")
            .setPositiveButton("Удалить") { dialog, _ ->
                adapter.deleteItem(item)
                dialog.dismiss()
            }
            .setNegativeButton("Отмена") { dialog, _ ->
                dialog.cancel()
            }
            .create()
            .show()
    }
}