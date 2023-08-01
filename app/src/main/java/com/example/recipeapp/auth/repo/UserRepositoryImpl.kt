package com.example.recipeapp.auth.repo

import com.example.recipeapp.auth.local.LocalSource
import com.example.recipeapp.auth.model.User

class UserRepositoryImpl(val localSource: LocalSource): UserRepository {
    override suspend fun insertUser(user: User) {
        localSource.insertUser(user)
    }
    override suspend fun hasUsername(username: String): Int {
        return localSource.hasUsername(username)
    }
    override suspend fun getUserByUsername(username: String): User {
        return localSource.getUserByUsername(username)
    }
}