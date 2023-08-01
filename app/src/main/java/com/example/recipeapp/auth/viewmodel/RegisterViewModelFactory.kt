package com.example.recipeapp.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipeapp.auth.repo.UserRepository
import java.lang.IllegalArgumentException

class RegisterViewModelFactory(private val userRepository: UserRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(RegisterViewmodel::class.java)) {
            RegisterViewmodel(userRepository) as T
        } else {
            throw IllegalArgumentException("No RegisterViewmodel found!")
        }
    }
}