package com.example.pw1.lesson4

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private enum class AuthMode { LOGIN, REGISTER }

@Composable
fun Lesson4Screen(modifier: Modifier = Modifier) {
    var mode by remember { mutableStateOf(AuthMode.LOGIN) }

    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var loginError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var confirmPasswordError by remember { mutableStateOf<String?>(null) }

    var isLoading by remember { mutableStateOf(false) }
    var resultMessage by remember { mutableStateOf<String?>(null) }
    var resultIsSuccess by remember { mutableStateOf(true) }

    val scope = rememberCoroutineScope()

    fun clearErrors() {
        loginError = null
        passwordError = null
        emailError = null
        confirmPasswordError = null
        resultMessage = null
    }

    fun submit() {
        clearErrors()
        loginError = AuthValidator.validateLogin(login)
        passwordError = AuthValidator.validatePassword(password)
        if (mode == AuthMode.REGISTER) {
            emailError = AuthValidator.validateEmail(email)
            confirmPasswordError = AuthValidator.validateConfirmPassword(password, confirmPassword)
        }

        val hasErrors = listOf(loginError, passwordError, emailError, confirmPasswordError).any { it != null }
        if (hasErrors) return

        // Стан завантаження демонструє обробку запиту (обмін з сервером тощо).
        scope.launch {
            isLoading = true
            delay(900)
            isLoading = false

            if (mode == AuthMode.LOGIN) {
                if (AuthValidator.isValidCredentials(login, password)) {
                    resultIsSuccess = true
                    resultMessage = "Вхід виконано успішно. Ласкаво просимо, $login!"
                } else {
                    resultIsSuccess = false
                    resultMessage = "Невірний логін або пароль оператора"
                }
            } else {
                resultIsSuccess = true
                resultMessage = "Реєстрацію оператора $login завершено. Тепер увійдіть у систему."
                mode = AuthMode.LOGIN
                password = ""
                confirmPassword = ""
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AuthHeader(mode = mode)

        AuthFields(
            mode = mode,
            login = login,
            onLoginChange = { login = it },
            loginError = loginError,
            password = password,
            onPasswordChange = { password = it },
            passwordError = passwordError,
            email = email,
            onEmailChange = { email = it },
            emailError = emailError,
            confirmPassword = confirmPassword,
            onConfirmPasswordChange = { confirmPassword = it },
            confirmPasswordError = confirmPasswordError
        )

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.padding(top = 8.dp))
        } else {
            Button(onClick = ::submit, modifier = Modifier.fillMaxWidth()) {
                Text(if (mode == AuthMode.LOGIN) "Увійти" else "Зареєструватися")
            }
        }

        resultMessage?.let { AuthResultMessage(text = it, isSuccess = resultIsSuccess) }

        TextButton(
            onClick = {
                mode = if (mode == AuthMode.LOGIN) AuthMode.REGISTER else AuthMode.LOGIN
                clearErrors()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                if (mode == AuthMode.LOGIN) "Немає акаунта? Зареєструватися"
                else "Вже є акаунт? Увійти"
            )
        }
    }
}

@Composable
private fun AuthHeader(mode: AuthMode, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = "Заняття №4 · Просунутий рівень",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = if (mode == AuthMode.LOGIN) "Вхід оператора СККЯЕ" else "Реєстрація оператора СККЯЕ",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Система контролю якості електроенергії",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun AuthFields(
    mode: AuthMode,
    login: String,
    onLoginChange: (String) -> Unit,
    loginError: String?,
    password: String,
    onPasswordChange: (String) -> Unit,
    passwordError: String?,
    email: String,
    onEmailChange: (String) -> Unit,
    emailError: String?,
    confirmPassword: String,
    onConfirmPasswordChange: (String) -> Unit,
    confirmPasswordError: String?,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        OutlinedTextField(
            value = login,
            onValueChange = onLoginChange,
            label = { Text("Логін оператора") },
            isError = loginError != null,
            supportingText = { loginError?.let { Text(it) } },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        if (mode == AuthMode.REGISTER) {
            OutlinedTextField(
                value = email,
                onValueChange = onEmailChange,
                label = { Text("Електронна пошта") },
                isError = emailError != null,
                supportingText = { emailError?.let { Text(it) } },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        }

        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
            label = { Text("Пароль") },
            isError = passwordError != null,
            supportingText = { passwordError?.let { Text(it) } },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        if (mode == AuthMode.REGISTER) {
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = onConfirmPasswordChange,
                label = { Text("Підтвердження пароля") },
                isError = confirmPasswordError != null,
                supportingText = { confirmPasswordError?.let { Text(it) } },
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun AuthResultMessage(text: String, isSuccess: Boolean, modifier: Modifier = Modifier) {
    Text(
        text = text,
        color = if (isSuccess) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
        style = MaterialTheme.typography.bodyMedium,
        fontWeight = FontWeight.Bold,
        modifier = modifier
    )
}
