package com.example.recipeapp.main.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeapp.R
import com.example.recipeapp.main.model.Favorite
import com.example.recipeapp.main.model.Meal
import com.example.recipeapp.main.viewmodel.RecipeViewModel
//import com.example.recipeapp.main.viewmodel.SearchViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MealAdapter(private val viewModel: RecipeViewModel): RecyclerView.Adapter<MealAdapter.MealViewHolder>() {
    private val data = mutableListOf<Meal>()
    class MealViewHolder(row: View): RecyclerView.ViewHolder(row) {
        val thumbnailHolder = row.findViewById<ImageView>(R.id.meal_thumbnail)
        val titleHolder = row.findViewById<TextView>(R.id.meal_title)
        val categoryHolder = row.findViewById<TextView>(R.id.meal_category)
        val areaHolder = row.findViewById<TextView>(R.id.meal_area)
        val favoriteButton = row.findViewById<CheckBox>(R.id.favorite_check_box)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealAdapter.MealViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.single_row, parent, false)
        return MealViewHolder(layout)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load(data[position])
            .into(holder.thumbnailHolder)
        holder.titleHolder.text = data[position].strMeal
        holder.categoryHolder.text = String.format(holder.itemView.resources.getString(R.string.category_str), data[position].strCategory)
        holder.areaHolder.text = String.format(holder.itemView.resources.getString(R.string.area_str), data[position].strArea)
        val prefs = holder.itemView.context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val userId = prefs.getInt("user_id", -1)
        viewModel.checkIfFavoite(userId, data[position].idMeal).observe(holder.itemView.context as LifecycleOwner, Observer {isFavorite ->
            holder.favoriteButton.isChecked = isFavorite
        })
        holder.favoriteButton.setOnCheckedChangeListener{buttonView, isChecked ->
            if (isChecked) {
                viewModel.addFavorite(Favorite(userId, data[position].idMeal))
            } else {
                MaterialAlertDialogBuilder(holder.itemView.context).setTitle("Confirm")
                    .setMessage("Are you sure you want to remove this item from your favorites?")
                    .setPositiveButton("Yes") { dialog, which ->
                        viewModel.deleteFavorite(Favorite(userId, data[position].idMeal))
                    }
                    .setNegativeButton("No") { dialog, which ->
                        holder.favoriteButton.isChecked = true
                    }.show()
            }
        }

    }

    fun setData(newData: List<Meal>) {
        data.clear()
        data.addAll(newData)
    }
}