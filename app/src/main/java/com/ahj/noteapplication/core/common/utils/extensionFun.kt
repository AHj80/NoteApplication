package com.ahj.noteapplication.core.common.utils

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Patterns
import androidx.core.net.toUri


fun Context.networkManager(): Boolean {

    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = connectivityManager.activeNetwork ?: return false
    val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

    return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
            capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
}

fun String.stripMarkdown(): String {
    return this
        // پاک کردن تگ‌های HTML مثل <u>, </u>
        .replace(Regex("<[^>]*>"), "")
        // پاک کردن هدرها (# Header)
        .replace(Regex("(?m)^#{1,6}\\s+"), "")
        // پاک کردن علامت‌های بولد و ایتالیک (**bold**, *italic*, __bold__, _italic_)
        .replace(Regex("(\\*\\*|\\*|__|_)"), "")
        // پاک کردن لینک‌ها [text](url) -> text
        .replace(Regex("\\[([^\\]]+)\\]\\([^\\)]+\\)"), "\$1")
        // پاک کردن کدهای خطی
        .replace(Regex("`{1,3}.*?`{1,3}"), "")
        .trim()
}

fun String.regexPass(): Boolean {

    val regex =
        this.isNotBlank() && this.any { it.isUpperCase()  } && this.any { it.isLowerCase() } && this.any { it.isDigit() } && this.length >= 8
    return regex
}

fun Context.openUrl(url: String, packageName: String? = null) {
    try {
        val intent = Intent(Intent.ACTION_VIEW, url.toUri()).apply {
            packageName?.let { setPackage(it) }
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivity(intent)
    } catch (e: Exception) {
        val fallbackIntent = Intent(Intent.ACTION_VIEW, url.toUri()).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivity(fallbackIntent)
    }
}

fun String.regexEmail(): Boolean{
    val regex = this.length > 9 && this.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(
        this
    ).matches()
    return regex
}
