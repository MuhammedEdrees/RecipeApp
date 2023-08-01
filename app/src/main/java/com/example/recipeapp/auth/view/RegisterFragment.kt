package com.example.recipeapp.auth.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.recipeapp.R
import com.example.recipeapp.auth.model.User
import com.example.recipeapp.auth.repo.UserRepository
import com.example.recipeapp.auth.repo.UserRepositoryImpl
import com.example.recipeapp.db.RecipeDatabase
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.runBlocking

class RegisterFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)
        view.findViewById<Button>(R.id.register_btn).setOnClickListener {
            val username: String = view.findViewById<TextInputEditText?>(R.id.username).text.toString()
            val email: String = view.findViewById<TextInputEditText?>(R.id.email).text.toString()
            val password: String = view.findViewById<TextInputEditText?>(R.id.password).text.toString()
            val confirmPassword: String = view.findViewById<TextInputEditText?>(R.id.confirm_password).text.toString()

            if(password == confirmPassword && username.isNotBlank() && email.isNotBlank() && password.isNotBlank() && confirmPassword.isNotBlank())
            {
                val dao = RecipeDatabase.getInstance(requireContext()).userDao()
                runBlocking {
                    dao.insertUser(User(username,email,password))
                }
                Toast.makeText(context,"this user has been added successfully =]", Toast.LENGTH_LONG).show()
            }
            else if(password != confirmPassword)
            {
                Toast.makeText(context,"the two passwords doesn't match", Toast.LENGTH_LONG).show()
            }
            else
            {
                Toast.makeText(context,"please fill all the fields", Toast.LENGTH_LONG).show()
            }
        }
        return view
    }
}