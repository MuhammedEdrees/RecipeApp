package com.example.recipeapp.auth.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.recipeapp.R
import com.example.recipeapp.auth.local.LocalSourceImpl
import com.example.recipeapp.auth.repo.UserRepositoryImpl
import com.example.recipeapp.auth.viewmodel.LoginViewModelFactory
import com.example.recipeapp.auth.viewmodel.LoginViewmodel
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class LoginFragment : Fragment() {
    lateinit var usernameTextInputLayout: TextInputLayout
    lateinit var usernameEditText: TextInputEditText
    lateinit var passwordTextInputLayout: TextInputLayout
    lateinit var passwordEditText: TextInputEditText
    lateinit var loginButton: MaterialButton
    lateinit var registerButton: MaterialButton
    lateinit var viewModel: LoginViewmodel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareViewModel()
        usernameTextInputLayout = view.findViewById(R.id.login_username_textfield)

        passwordTextInputLayout = view.findViewById(R.id.login_password_textfield)

        loginButton = view.findViewById(R.id.login_button)
        loginButton.setOnClickListener {
            val username  = ""
            val password = ""
            if(viewModel.verifyUsernameExists(username)) {
                val user = viewModel.getUserByUsername(username)
                if(password == user.password) {
                    TODO("Login")
                } else {
                    TODO("Show Invalid Password")
                }
            } else {
                TODO("Show Invalid Username")
            }
        }
        registerButton.setOnClickListener {
            TODO("Navigate to Register Fragment")
        }
    }
    fun prepareViewModel(){
        val factory = LoginViewModelFactory(UserRepositoryImpl(LocalSourceImpl(requireActivity())))
        viewModel = ViewModelProvider(requireActivity() as ViewModelStoreOwner, factory).get(LoginViewmodel::class.java)
    }
}