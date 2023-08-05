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
import com.example.recipeapp.auth.view.util.Validation
import com.example.recipeapp.auth.viewmodel.RegisterViewModelFactory
import com.example.recipeapp.auth.viewmodel.RegisterViewmodel
import com.example.recipeapp.db.RecipeDatabase
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.runBlocking
import java.util.regex.Pattern
import java.util.regex.Matcher

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

            var count = 0

            if(username.isEmpty())
            {
                view.findViewById<TextInputLayout>(R.id.text_username_layout).error = "this field is required"
            }
            else if(!Validation.UsernameValidation.validateAll(username))
            {
                if(!Validation.UsernameValidation.validateLength(username))
                {
                    view.findViewById<TextInputLayout>(R.id.text_username_layout).error = "Username is not valid!\nit should be more that 8 digits"
                }
                else
                {
                    view.findViewById<TextInputLayout>(R.id.text_username_layout).error = "Username is not valid!\nit should contain only lower or upper case letters and numbers and under score"
                }
            }
            else
            {
                view.findViewById<TextInputLayout>(R.id.text_username_layout).error = null
                count++
            }

            if(email.isEmpty())
            {
                view.findViewById<TextInputLayout>(R.id.text_email_layout).error = "this field is required"
            }
            else if(!Validation.EmailValidation.validateAll(email))
            {
                view.findViewById<TextInputLayout>(R.id.text_email_layout).error = "Email is not valid!"
            }
            else
            {
                view.findViewById<TextInputLayout>(R.id.text_email_layout).error = null
                count++
            }

            if(password.isEmpty())
            {
                view.findViewById<TextInputLayout>(R.id.text_pass_layout).error = "this field is required"
            }
            else if(!Validation.PasswordValidation.validateAll(password))
            {
                if(!Validation.PasswordValidation.validateLength(password))
                {
                    view.findViewById<TextInputLayout>(R.id.text_pass_layout).error = "Password is not valid!\nit should be more that 8 digits"
                }
                else
                {
                    view.findViewById<TextInputLayout>(R.id.text_pass_layout).error = "password is not valid!\nit should contain at least one upper case letter and one lower case letter and special character and one number"
                }
            }
            else
            {
                view.findViewById<TextInputLayout>(R.id.text_pass_layout).error = null
                count++
            }

            if(confirmPassword.isEmpty())
            {
                view.findViewById<TextInputLayout>(R.id.text_confirm_pass_layout).error = "this field is required"
            }
            else if(password != confirmPassword)
            {
                view.findViewById<TextInputLayout>(R.id.text_confirm_pass_layout).error = "these two password doesn't match"
            }
            else
            {
                view.findViewById<TextInputLayout>(R.id.text_confirm_pass_layout).error = null
                count++
            }

            if(count == 4)
            {

                viewModel.verifyUsernameExists(username).observe(viewLifecycleOwner, Observer { result ->
                    if (result) {
                        view.findViewById<TextInputLayout>(R.id.text_username_layout).error = "this username has been used before"
                    }
                    else
                    {
                        val dao = RecipeDatabase.getInstance(requireContext()).userDao()
                        runBlocking {
                            dao.insertUser(User(username, email, password))
                        }
                        Toast.makeText(
                            context,
                            "this user has been added successfully =]",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
            }
        }
        return view
    }

    fun prepareViewModel() {
        val factory = RegisterViewModelFactory(UserRepositoryImpl(UserLocalSourceImpl(requireActivity())))
        viewModel = ViewModelProvider(requireActivity() as ViewModelStoreOwner, factory).get(RegisterViewmodel::class.java)
    }
}