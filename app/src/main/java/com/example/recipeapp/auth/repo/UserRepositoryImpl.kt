package com.example.recipeapp.auth.repo

import com.example.recipeapp.auth.local.UserLocalSource
import com.example.recipeapp.auth.model.User

class UserRepositoryImpl(private val userLocalSource: UserLocalSource) : UserRepository {
    override suspend fun insertUser(user: User) {
        userLocalSource.insertUser(user)
    }

    override suspend fun hasUsername(username: String): Int {
        return userLocalSource.hasUsername(username)
    }

    override suspend fun getUserByUsername(username: String): User {
        return userLocalSource.getUserByUsername(username)
    }
}