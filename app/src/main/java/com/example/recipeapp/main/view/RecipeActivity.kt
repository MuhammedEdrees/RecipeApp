package com.example.recipeapp.main.view

import android.app.ActionBar.LayoutParams
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.recipeapp.R
import com.example.recipeapp.auth.view.AuthenticationActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

class RecipeActivity : AppCompatActivity() {
    lateinit var topBar: MaterialToolbar
    lateinit var navController: NavController
    lateinit var bottomNavigation: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)
        topBar = findViewById<MaterialToolbar>(R.id.top_app_bar)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.activity_recipe_nav_host) as NavHostFragment
        navController = navHostFragment.navController
        bottomNavigation= findViewById(R.id.bottom_navigation)
        bottomNavigation.setupWithNavController(navController)
        setSupportActionBar(topBar)
        setupActionBarWithNavController(navController)
        navController.addOnDestinationChangedListener{_, destination, _ ->
            when(destination.id) {
                R.id.homeFragment -> {
                    showBarAndBottomNavigation()
                    supportActionBar?.title = "Home"
                }
                R.id.searchFragment -> {
                    showBarAndBottomNavigation()
                    supportActionBar?.title = "Search"
                }
                R.id.favoriteFragment -> {
                    showBarAndBottomNavigation()
                    supportActionBar?.title = "Favorites"
                }
                R.id.detailsFragment -> {
                    hideBarAndBottomNavigation()
                }
                R.id.aboutUsFragment -> {
                    hideBarAndBottomNavigation()
                }
            }
        }
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
                findNavController(R.id.activity_recipe_nav_host).navigate(R.id.aboutUsFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    fun hideBarAndBottomNavigation() {
        supportActionBar?.hide()
        bottomNavigation.visibility = View.GONE
    }
    fun showBarAndBottomNavigation() {
        supportActionBar?.show()
        bottomNavigation.visibility = View.VISIBLE
    }
}