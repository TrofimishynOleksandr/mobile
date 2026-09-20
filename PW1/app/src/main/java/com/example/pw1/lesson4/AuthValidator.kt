package com.example.pw1.lesson4

object AuthValidator {

    private const val DEMO_LOGIN = "operator"
    private const val DEMO_PASSWORD = "quality2026"

    fun validateLogin(login: String): String? = when {
        login.isBlank() -> "Введіть логін оператора"
        login.length < 3 -> "Логін має містити щонайменше 3 символи"
        else -> null
    }

    fun validatePassword(password: String): String? = when {
        password.isBlank() -> "Введіть пароль"
        password.length < 6 -> "Пароль має містити щонайменше 6 символів"
        else -> null
    }

    fun validateEmail(email: String): String? = when {
        email.isBlank() -> "Введіть електронну пошту"
        !email.contains("@") || !email.contains(".") -> "Некоректний формат електронної пошти"
        else -> null
    }

    fun validateConfirmPassword(password: String, confirmPassword: String): String? = when {
        confirmPassword.isBlank() -> "Підтвердіть пароль"
        confirmPassword != password -> "Паролі не співпадають"
        else -> null
    }

    fun isValidCredentials(login: String, password: String): Boolean =
        login == DEMO_LOGIN && password == DEMO_PASSWORD
}
