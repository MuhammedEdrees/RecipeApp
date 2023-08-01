package com.example.recipeapp.auth.local

import android.content.Context
import com.example.recipeapp.auth.model.User
import com.example.recipeapp.db.RecipeDatabase
import com.example.recipeapp.db.UserDao

class LocalSourceImpl(val context: Context): LocalSource {
    lateinit private var dao: UserDao
    init {
        dao = RecipeDatabase.getInstance(context).userDao()
    }
    override suspend fun insertUser(user: User) {
        dao.insertUser(user)
    }
}