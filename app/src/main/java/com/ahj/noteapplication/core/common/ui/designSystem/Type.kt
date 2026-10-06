package com.ahj.noteapplication.core.common.ui.designSystem

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.sp
import com.ahj.noteapplication.R


val vazirmatn = FontFamily(
    Font(
        R.font.vazirmatn
    )
)


val customTypography = Typography(
    labelMedium = TextStyle(
        fontFamily = vazirmatn,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 24.sp,
        letterSpacing = 1.sp,
        textDirection = TextDirection.Ltr,
    ),
    bodyLarge = TextStyle(
        fontFamily = vazirmatn,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 1.sp,
        textDirection = TextDirection.Rtl
    ),
    titleLarge = TextStyle(
        fontFamily = vazirmatn,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        lineHeight = 28.sp,
        letterSpacing = 1.sp,
        textDirection = TextDirection.Rtl

    ),

    )