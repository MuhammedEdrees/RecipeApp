package com.example.myapplication3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R

class TableAdapter(private val data: List<IngredientRow>) :
    RecyclerView.Adapter<TableAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ingredientColumn: TextView = itemView.findViewById(R.id.ingredient)
        val measureColumn: TextView = itemView.findViewById(R.id.measure)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.ingredient_row,
            parent,
            false
        )
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val rowData = data[position]
        holder.ingredientColumn.text = rowData.ingredient
        holder.measureColumn.text = rowData.measure
    }

    override fun getItemCount() = data.size
}
