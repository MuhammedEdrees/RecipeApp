package com.example.recipeapp.auth.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User (
    val username: String,
    val email: String,
    val password: String ,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0)
