package com.example.pw2.lesson5

sealed class Screen(val route: String) {
    object MainMenu : Screen("main_menu")
    object ReadingInput : Screen("reading_input")
    object Report : Screen("report/{voltage}/{frequency}")
}
