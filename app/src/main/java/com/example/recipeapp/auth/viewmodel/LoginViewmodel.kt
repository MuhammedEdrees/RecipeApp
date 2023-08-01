package com.example.recipeapp.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.auth.repo.UserRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class LoginViewmodel(val repo: UserRepository): ViewModel() {
    fun verifyUsernameExists(username: String) = runBlocking{
        val res = viewModelScope.async {
            repo.hasUsername(username) == 1
        }
        res.await()
    }
    fun getUserByUsername(username: String) = runBlocking {
        val user = viewModelScope.async {
            repo.getUserByUsername(username)
        }
        user.await()
    }
}