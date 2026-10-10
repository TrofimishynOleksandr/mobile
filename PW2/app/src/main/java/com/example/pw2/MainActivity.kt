package com.example.pw2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.pw2.lesson5.Lesson5App
import com.example.pw2.lesson6.Lesson6App
import com.example.pw2.lesson7.Lesson7App
import com.example.pw2.lesson8.Lesson8App
import com.example.pw2.ui.theme.PW2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PW2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        LessonTabs()
                    }
                }
            }
        }
    }
}

private val lessonTitles = listOf("Заняття 5", "Заняття 6", "Заняття 7", "Заняття 8")

@Composable
fun LessonTabs() {
    var selectedTab by remember { mutableIntStateOf(0) }

    TabRow(selectedTabIndex = selectedTab) {
        lessonTitles.forEachIndexed { index, title ->
            Tab(
                selected = selectedTab == index,
                onClick = { selectedTab = index },
                text = { Text(title) }
            )
        }
    }

    when (selectedTab) {
        0 -> Lesson5App()
        1 -> Lesson6App()
        2 -> Lesson7App()
        3 -> Lesson8App()
    }
}