package com.example.recipeapp.auth.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.recipeapp.R
import com.example.recipeapp.auth.local.UserLocalSourceImpl
import com.example.recipeapp.auth.model.User
import com.example.recipeapp.auth.repo.UserRepositoryImpl
import com.example.recipeapp.auth.viewmodel.RegisterViewModelFactory
import com.example.recipeapp.auth.viewmodel.RegisterViewmodel
import com.example.recipeapp.db.RecipeDatabase
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.runBlocking

class RegisterFragment : Fragment() {
    lateinit var viewModel: RegisterViewmodel

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
            val repo = UserRepositoryImpl(UserLocalSourceImpl(requireActivity()))

            prepareViewModel()

            if(password == confirmPassword && username.isNotBlank() && email.isNotBlank() && password.isNotBlank() && confirmPassword.isNotBlank())
            {
                view.findViewById<TextInputLayout>(R.id.text_confirm_pass_layout).error = null
                viewModel.verifyUsernameExists(username).observe(viewLifecycleOwner, Observer { result ->
                    if (!result) {
                        view.findViewById<TextInputLayout>(R.id.text_username_layout).error = null
                        val dao = RecipeDatabase.getInstance(requireContext()).userDao()
                        runBlocking {
                            dao.insertUser(User(username,email,password))
                        }
                        Toast.makeText(context,"this user has been added successfully =]", Toast.LENGTH_LONG).show()
                    } else {
                        view.findViewById<TextInputLayout>(R.id.text_username_layout).error = "Username already exists!"
                    }
                })
            } else if(password != confirmPassword) {
                view.findViewById<TextInputLayout>(R.id.text_confirm_pass_layout).error = "the two passwords don't match"
            } else {
                Toast.makeText(context,"please fill all the fields", Toast.LENGTH_LONG).show()
            }
        }
        return view
    }

    fun prepareViewModel() {
        val factory = RegisterViewModelFactory(UserRepositoryImpl(UserLocalSourceImpl(requireActivity())))
        viewModel = ViewModelProvider(requireActivity() as ViewModelStoreOwner, factory).get(
            RegisterViewmodel::class.java)
    }
}