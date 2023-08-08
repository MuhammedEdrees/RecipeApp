package com.example.recipeapp.main.view

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeapp.R
import com.example.recipeapp.main.model.Favorite
import com.example.recipeapp.main.model.Meal
import com.example.recipeapp.main.viewmodel.RecipeViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MealAdapter(private val viewModel: RecipeViewModel, private val owner: LifecycleOwner): RecyclerView.Adapter<MealAdapter.MealViewHolder>() {
    private val data = mutableListOf<Meal>()

    class MealViewHolder(row: View) : RecyclerView.ViewHolder(row) {
        val thumbnailHolder = row.findViewById<ImageView>(R.id.favorite_thumbnail)
        val titleHolder = row.findViewById<TextView>(R.id.favorite_title)
        val categoryHolder = row.findViewById<TextView>(R.id.favorite_category_txt)
        val areaHolder = row.findViewById<TextView>(R.id.favorite_area_txt)
        val favoriteButton = row.findViewById<CheckBox>(R.id.favorite_check_box)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealAdapter.MealViewHolder {
        val layout =
            LayoutInflater.from(parent.context).inflate(R.layout.favorite_list_item, parent, false)
        return MealViewHolder(layout)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load(data[position].strMealThumb)
            .into(holder.thumbnailHolder)
        Log.d("edrees -->", "Thumbnail Loaded")
        holder.titleHolder.text = data[position].strMeal
        holder.categoryHolder.text = data[position].strCategory
        holder.areaHolder.text = data[position].strArea
        val prefs = holder.itemView.context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val userId = prefs.getInt("user_id", -1)
        /*viewModel.listOfFavorites.value?.forEach {
            if(data[position].idMeal == it.mealID) {
                holder.favoriteButton.isChecked = true
            }
        }*/
        holder.favoriteButton.setOnCheckedChangeListener { buttonView, isChecked ->
            holder.favoriteButton.setOnCheckedChangeListener(null)
            holder.favoriteButton.isChecked =
                viewModel.listOfFavorites.value?.any { it.mealID == data[position].idMeal } ?: false
            holder.favoriteButton.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    viewModel.addFavorite(Favorite(userId, data[position].idMeal), data[position])
                    viewModel.getUserFavorites(userId)
                } else {
                    MaterialAlertDialogBuilder(holder.itemView.context).setTitle("Confirm")
                        .setMessage("Are you sure you want to remove this item from your favorites?")
                        .setPositiveButton("Yes") { dialog, which ->
                            viewModel.deleteFavorite(Favorite(userId, data[position].idMeal))
                        }
                        .setNegativeButton("No") { dialog, which ->
                            buttonView.isChecked = true
                        }.show()
                }
            }
        }
    }

        fun setData(newData: List<Meal>) {
            data.clear()
            data.addAll(newData)
            notifyDataSetChanged()
        }
    }
