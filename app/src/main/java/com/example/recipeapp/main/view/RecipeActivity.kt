package com.example.recipeapp.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.recipeapp.R
import com.google.android.material.appbar.MaterialToolbar

class RecipeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)
        val topBar = findViewById<MaterialToolbar>(R.id.top_app_bar)
        setSupportActionBar(topBar)
        supportActionBar?.title = "Recipe App"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.sign_out -> {
                TODO("Sign Out Implementation")
                true
            }
            R.id.about_us -> {
                TODO("About as")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}