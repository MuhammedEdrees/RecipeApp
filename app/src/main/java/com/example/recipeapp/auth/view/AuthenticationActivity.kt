package com.example.recipeapp.auth.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.example.recipeapp.R

class AuthenticationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.auth_fragments_container_view) as NavHostFragment
        val navController = navHostFragment.navController


        val navGraph = navController.navInflater.inflate(R.navigation.auth_nav_graph)
        navController.graph = navGraph
    }
}