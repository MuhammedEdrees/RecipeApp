package com.example.recipeapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.recipeapp.auth.dao.UserDao
import com.example.recipeapp.auth.model.User
import com.example.recipeapp.main.dao.FavoriteDao
import com.example.recipeapp.main.dao.MealDao
import com.example.recipeapp.main.model.Favorite
import com.example.recipeapp.main.model.Meal

@Database(entities = [User::class, Meal::class, Favorite::class], version = 2)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    abstract fun mealDao(): MealDao

    abstract fun favoriteDao(): FavoriteDao

    companion object {
        @Volatile
        private var INSTANCE: RecipeDatabase? = null
        fun getInstance(context: Context): RecipeDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    RecipeDatabase::class.java,
                    "recipe_db"
                ).fallbackToDestructiveMigration().build().also {
                    INSTANCE = it
                }
            }
        }

    }
}