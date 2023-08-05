package com.example.recipeapp.auth.view.util


object Validation {
    object UsernameValidation
    {
        fun validateLength(username: String) = username.length > 8
        fun validateChars(username: String) = username.matches("[a-zA-Z0-9_]+".toRegex())
        fun validateAll(username: String) = validateLength(username) && validateChars(username)
    }

    object PasswordValidation
    {
        fun validateLength(password: String) = password.length > 8
        fun validateChars(password: String) = password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[^a-zA-Z0-9]).*\$".toRegex())
        fun validateAll(password: String) = validateLength(password) && validateChars(password)
    }

    object EmailValidation
    {
        fun validateAll(email: String) = email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\$".toRegex())
    }
}