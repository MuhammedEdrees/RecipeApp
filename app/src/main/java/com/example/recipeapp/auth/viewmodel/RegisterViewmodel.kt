package com.example.recipeapp.auth.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.auth.repo.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class RegisterViewmodel @Inject constructor(val repo: UserRepository): ViewModel() {
    fun verifyUsernameExists(username: String):  MutableLiveData<Boolean>{
        val result = MutableLiveData<Boolean>()
        viewModelScope.launch {
            result.value = repo.hasUsername(username) == 1
        }
        return result
    }

}