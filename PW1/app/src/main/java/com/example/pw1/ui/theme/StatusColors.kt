package com.example.pw1.ui.theme

import androidx.compose.ui.graphics.Color
import com.example.pw1.data.QualityStatus

val StatusNormalColor = Color(0xFF2E7D32)
val StatusWarningColor = Color(0xFFF9A825)
val StatusCriticalColor = Color(0xFFC62828)

fun colorForStatus(status: QualityStatus): Color = when (status) {
    QualityStatus.NORMAL -> StatusNormalColor
    QualityStatus.WARNING -> StatusWarningColor
    QualityStatus.CRITICAL -> StatusCriticalColor
}

fun labelForStatus(status: QualityStatus): String = when (status) {
    QualityStatus.NORMAL -> "Норма"
    QualityStatus.WARNING -> "Попередження"
    QualityStatus.CRITICAL -> "Критичне відхилення"
}
