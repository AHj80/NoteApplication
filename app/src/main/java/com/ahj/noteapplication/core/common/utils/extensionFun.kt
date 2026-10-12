package com.ahj.noteapplication.core.common.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities


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

