package com.example.recipeapp.main.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "favorites", primaryKeys = ["userID", "mealID"])
data class Favorite(
    val userID: Int,
    val mealID: String
)
