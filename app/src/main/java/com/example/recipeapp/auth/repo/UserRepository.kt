package com.example.recipeapp.auth.repo

import com.example.recipeapp.auth.model.User

interface UserRepository {
    suspend fun insertUser(user: User)
}