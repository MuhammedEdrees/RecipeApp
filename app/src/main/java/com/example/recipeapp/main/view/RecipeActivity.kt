package com.example.recipeapp.main.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.findNavController
import com.example.recipeapp.R
import com.example.recipeapp.auth.view.AuthenticationActivity
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
                val prefs = getSharedPreferences("user_prefs", MODE_PRIVATE)
                val editor = prefs?.edit()
                editor?.clear()
                editor?.apply()
                val intent = Intent(this, AuthenticationActivity::class.java)
                startActivity(intent)
                finish()
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