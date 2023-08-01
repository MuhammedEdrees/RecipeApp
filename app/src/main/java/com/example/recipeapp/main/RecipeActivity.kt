package com.example.recipeapp.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.recipeapp.R

class RecipeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)
    }

    override fun onBackPressed() {
        finish()
    }
}