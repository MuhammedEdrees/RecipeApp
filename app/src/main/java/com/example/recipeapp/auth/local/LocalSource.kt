package com.example.recipeapp.auth.local

import com.example.recipeapp.auth.model.User

interface LocalSource {
    suspend fun insertUser(vararg users: User)
}