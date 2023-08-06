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
import com.example.recipeapp.main.viewmodel.FavoriteViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class FavoriteMealAdapter(val viewModel: FavoriteViewModel) : RecyclerView.Adapter<FavoriteMealAdapter.FavoriteMealViewHolder>() {
    private val data = mutableListOf<Meal>()
    class FavoriteMealViewHolder(val row: View): RecyclerView.ViewHolder(row) {
        val favThumbnail = row.findViewById<ImageView>(R.id.favorite_thumbnail)
        val favTitle = row.findViewById<TextView>(R.id.favorite_title)
        val favCategory = row.findViewById<TextView>(R.id.favorite_category_txt)
        val favArea = row.findViewById<TextView>(R.id.favorite_area_txt)
        val favButton = row.findViewById<CheckBox>(R.id.favorite_check_box)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMealViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.favorite_list_item, parent, false)
        return FavoriteMealViewHolder(view)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: FavoriteMealViewHolder, position: Int) {
        holder.favButton.setOnCheckedChangeListener(null)
        Glide.with(holder.itemView.context)
            .load(data[position].strMealThumb)
            .into(holder.favThumbnail)
        holder.favTitle.text = data[position].strMeal
        holder.favCategory.text = data[position].strCategory
        holder.favArea.text = data[position].strArea
        holder.favButton.isChecked = true
        val prefs = holder.itemView.context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val userId = prefs.getInt("user_id", -1)
        holder.favButton.setOnCheckedChangeListener{buttonView, isChecked ->
            if (isChecked) {
                viewModel.addFavorite(Favorite(userId, data[position].idMeal), data[position])
            } else {
                MaterialAlertDialogBuilder(holder.itemView.context).setTitle("Confirm")
                    .setMessage("Are you sure you want to remove this item from your favorites?")
                    .setPositiveButton("Yes") { dialog, which ->
                        viewModel.deleteFavorite(Favorite(userId, data[position].idMeal))
                        data.removeAt(position)
                        notifyDataSetChanged()
                    }
                    .setNegativeButton("No") { dialog, which ->
                        dialog.cancel()
                        buttonView.isChecked = true
                    }.show()
            }
        }
    }

    fun setData(newData: List<Meal>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }
}