package com.example.recipeapp.auth.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.auth.model.User
import com.example.recipeapp.auth.repo.UserRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class LoginViewmodel(val repo: UserRepository): ViewModel() {
    fun verifyUsernameExists(username: String): MutableLiveData<Boolean> {
        val result = MutableLiveData<Boolean>()
        viewModelScope.launch {
            result.value = repo.hasUsername(username) == 1
        }
        return result
    }
    fun getUserByUsername(username: String): MutableLiveData<User> {
        val user = MutableLiveData<User>()
        viewModelScope.launch {
            user.value = repo.getUserByUsername(username)
        }
        return user
    }
}