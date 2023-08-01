package com.example.recipeapp.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipeapp.auth.repo.UserRepository
import java.lang.IllegalArgumentException

class LoginViewModelFactory(private val userRepository: UserRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(LoginViewmodel::class.java)) {
            LoginViewmodel(userRepository) as T
        } else {
            throw IllegalArgumentException("No LoginViewmodel found!")
        }
    }
}