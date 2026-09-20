package com.example.pw1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.pw1.lesson1.Lesson1Screen
import com.example.pw1.lesson2.Lesson2Screen
import com.example.pw1.lesson3.Lesson3Screen
import com.example.pw1.lesson4.Lesson4Screen
import com.example.pw1.ui.theme.PW1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PW1Theme {
                PracticalWorkApp()
            }
        }
    }
}

private val lessonTitles = listOf("Заняття 1", "Заняття 2", "Заняття 3", "Заняття 4")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PracticalWorkApp() {
    var selectedLesson by remember { mutableIntStateOf(0) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = { Text("Контроль якості електроенергії") })
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            TabRow(selectedTabIndex = selectedLesson) {
                lessonTitles.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedLesson == index,
                        onClick = { selectedLesson = index },
                        text = { Text(title) }
                    )
                }
            }

            when (selectedLesson) {
                0 -> Lesson1Screen()
                1 -> Lesson2Screen()
                2 -> Lesson3Screen()
                else -> Lesson4Screen()
            }
        }
    }
}
