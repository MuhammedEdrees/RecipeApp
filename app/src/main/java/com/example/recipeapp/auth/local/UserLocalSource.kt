package com.example.recipeapp.auth.local

import com.example.recipeapp.auth.model.User

interface UserLocalSource {
    suspend fun insertUser(user: User)
    suspend fun hasUsername(username: String): Int
    suspend fun getUserByUsername(username: String): User
}