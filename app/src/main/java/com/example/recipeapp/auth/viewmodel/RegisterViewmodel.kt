package com.example.recipeapp.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.auth.repo.UserRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class RegisterViewmodel(val repo: UserRepository): ViewModel() {
    fun verifyUsernameExists(username: String) = runBlocking{
        val res = viewModelScope.async {
            repo.hasUsername(username) == 1
        }
        res.await()
    }
}