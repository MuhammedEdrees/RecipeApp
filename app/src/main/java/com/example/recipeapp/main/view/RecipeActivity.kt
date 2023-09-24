package com.example.recipeapp.main.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.recipeapp.R
import com.example.recipeapp.auth.view.AuthenticationActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeActivity : AppCompatActivity() {
    private lateinit var topBar: MaterialToolbar
    private lateinit var navController: NavController
    private lateinit var bottomNavigation: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)
        topBar = findViewById(R.id.top_app_bar)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.activity_recipe_nav_host) as NavHostFragment
        navController = navHostFragment.navController
        bottomNavigation = findViewById(R.id.bottom_navigation)
        bottomNavigation.setupWithNavController(navController)
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        topBar.setupWithNavController(
            navController, appBarConfiguration
        )
        topBar.inflateMenu(R.menu.options_menu)
        topBar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.sign_out -> {
                    val userPrefs = getSharedPreferences("user_prefs", MODE_PRIVATE)
                    userPrefs.edit().clear().apply()
                    val intent = Intent(this, AuthenticationActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }

                R.id.about_us -> {
                    findNavController(R.id.activity_recipe_nav_host).navigate(R.id.aboutUsFragment)
                    true
                }

                else -> super.onOptionsItemSelected(item)
            }
        }
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.detailsFragment, R.id.aboutUsFragment -> {
                    hideBarAndBottomNavigation()
                }

                else -> {
                    showBarAndBottomNavigation()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return true
    }

    private fun hideBarAndBottomNavigation() {
        topBar.visibility = View.GONE
        bottomNavigation.visibility = View.GONE
    }

    fun showBarAndBottomNavigation() {
        topBar.visibility = View.VISIBLE
        bottomNavigation.visibility = View.VISIBLE
    }

}