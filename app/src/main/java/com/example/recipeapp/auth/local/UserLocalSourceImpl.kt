package com.example.recipeapp.auth.local

import android.content.Context
import com.example.recipeapp.auth.dao.UserDao
import com.example.recipeapp.auth.model.User
import com.example.recipeapp.db.RecipeDatabase
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class UserLocalSourceImpl @Inject constructor(@ApplicationContext val context: Context) : UserLocalSource {
    private var dao: UserDao = RecipeDatabase.getInstance(context).userDao()

    override suspend fun insertUser(user: User) {
        dao.insertUser(user)
    }

    override suspend fun hasUsername(username: String) = dao.hasUsername(username)
    override suspend fun getUserByUsername(username: String): User {
        return dao.getUserByUsername(username)
    }
}