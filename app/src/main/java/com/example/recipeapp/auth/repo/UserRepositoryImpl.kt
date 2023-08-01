package com.example.recipeapp.auth.repo

import com.example.recipeapp.auth.local.LocalSource
import com.example.recipeapp.auth.model.User

class UserRepositoryImpl(val localSource: LocalSource): UserRepository {
    override suspend fun insertUser(user: User) {
        localSource.insertUser(user)
    }
}