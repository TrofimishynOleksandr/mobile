package com.example.pw1.lesson1

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.pw1.data.QualityCalculator
import com.example.pw1.ui.theme.colorForStatus
import com.example.pw1.ui.theme.labelForStatus
import kotlin.math.roundToInt

@Composable
fun Lesson1Screen(modifier: Modifier = Modifier) {
    val calculator = remember { QualityCalculator() }
    var reading by remember { mutableStateOf(calculator.generateReading()) }

    val status = calculator.resolveStatus(reading)
    val qualityIndex = calculator.calculateQualityIndex(reading)

    val animatedIndex by animateFloatAsState(
        targetValue = qualityIndex.toFloat(),
        animationSpec = tween(durationMillis = 600),
        label = "qualityIndexAnimation"
    )
    val animatedColor by animateColorAsState(
        targetValue = colorForStatus(status),
        animationSpec = tween(durationMillis = 600),
        label = "statusColorAnimation"
    )

    Surface(
        modifier = modifier.fillMaxSize(),
        color = animatedColor.copy(alpha = 0.08f)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Контроль якості електроенергії",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Заняття №1 · Просунутий рівень",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "${animatedIndex.roundToInt()}",
                style = MaterialTheme.typography.displayLarge,
                color = animatedColor,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Індекс якості електроенергії, %",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            Surface(
                color = animatedColor,
                shape = RoundedCornerShape(50),
            ) {
                Text(
                    text = labelForStatus(status),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
                    color = MaterialTheme.colorScheme.surface,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = { reading = calculator.generateReading() },
                colors = ButtonDefaults.buttonColors(containerColor = animatedColor)
            ) {
                Text("Оновити показники мережі")
            }
        }
    }
}
