package com.example.recipeapp.main.view

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeapp.R
import com.example.recipeapp.main.model.Favorite
import com.example.recipeapp.main.model.Meal
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class SearchMealAdapter(private val fragment: SearchMealCallback): RecyclerView.Adapter<SearchMealAdapter.MealViewHolder>() {
    private val data = mutableListOf<Meal>()
    class MealViewHolder(row: View): RecyclerView.ViewHolder(row) {
        val thumbnailHolder = row.findViewById<ImageView>(R.id.favorite_thumbnail)
        val titleHolder = row.findViewById<TextView>(R.id.favorite_title)
        val categoryHolder = row.findViewById<TextView>(R.id.favorite_category_txt)
        val areaHolder = row.findViewById<TextView>(R.id.favorite_area_txt)
        val favoriteButton = row.findViewById<CheckBox>(R.id.favorite_check_box)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.favorite_list_item, parent, false)
        return MealViewHolder(layout)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load(data[position].strMealThumb)
            .into(holder.thumbnailHolder)
        holder.titleHolder.text = data[position].strMeal
        holder.categoryHolder.text = data[position].strCategory
        holder.areaHolder.text = data[position].strArea
        val prefs = holder.itemView.context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val userId = prefs.getInt("user_id", -1)
        holder.favoriteButton.setOnCheckedChangeListener(null)
        holder.itemView.setOnClickListener(null)
        holder.favoriteButton.isChecked = fragment.isFavoriteCallback(data[position].idMeal)
        holder.favoriteButton.setOnCheckedChangeListener{ buttonView, isChecked ->
            if (isChecked) {
                fragment.addFavoriteCallback(Favorite(userId, data[position].idMeal), data[position])
            } else {
                MaterialAlertDialogBuilder(holder.itemView.context).setTitle("Confirm")
                    .setMessage("Are you sure you want to remove this item from your favorites?")
                    .setPositiveButton("Yes") { dialog, which ->
                        fragment.deleteFavoriteCallback(Favorite(userId, data[position].idMeal))
                    }
                    .setNegativeButton("No") { dialog, which ->
                        buttonView.isChecked = true
                    }.show()
            }
        }
        holder.itemView.setOnClickListener {
            fragment.navigateToDetailsCallback(data[position])
        }
    }

    fun setData(newData: List<Meal>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }
}