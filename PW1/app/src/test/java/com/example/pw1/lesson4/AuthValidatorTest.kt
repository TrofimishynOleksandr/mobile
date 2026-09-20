package com.example.pw1.lesson4

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class AuthValidatorTest {

    @Test
    fun pw_validateLogin_blank_returnsError() {
        assertEquals("Введіть логін оператора", AuthValidator.validateLogin(""))
    }

    @Test
    fun pw_validateLogin_tooShort_returnsError() {
        assertEquals("Логін має містити щонайменше 3 символи", AuthValidator.validateLogin("ab"))
    }

    @Test
    fun pw_validateLogin_valid_returnsNull() {
        assertNull(AuthValidator.validateLogin("operator"))
    }

    @Test
    fun pw_validatePassword_tooShort_returnsError() {
        assertEquals("Пароль має містити щонайменше 6 символів", AuthValidator.validatePassword("12345"))
    }

    @Test
    fun pw_validatePassword_valid_returnsNull() {
        assertNull(AuthValidator.validatePassword("123456"))
    }

    @Test
    fun pw_validateEmail_withoutAt_returnsError() {
        assertEquals("Некоректний формат електронної пошти", AuthValidator.validateEmail("bad-email"))
    }

    @Test
    fun pw_validateEmail_valid_returnsNull() {
        assertNull(AuthValidator.validateEmail("dispatcher@power.ua"))
    }

    @Test
    fun pw_validateConfirmPassword_mismatch_returnsError() {
        assertEquals("Паролі не співпадають", AuthValidator.validateConfirmPassword("abc123", "xyz"))
    }

    @Test
    fun pw_validateConfirmPassword_match_returnsNull() {
        assertNull(AuthValidator.validateConfirmPassword("abc123", "abc123"))
    }

    @Test
    fun pw_isValidCredentials_demoAccount_isTrue() {
        assertTrue(AuthValidator.isValidCredentials("operator", "quality2026"))
    }

    @Test
    fun pw_isValidCredentials_wrongPassword_isFalse() {
        assertFalse(AuthValidator.isValidCredentials("operator", "wrongpass"))
    }
}
