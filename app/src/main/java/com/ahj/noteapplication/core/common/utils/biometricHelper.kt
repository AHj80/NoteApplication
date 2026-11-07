package com.ahj.noteapplication.core.common.utils

import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity

fun biometricHelper(
    activity: FragmentActivity,
    title: String,
    onSuccess: () -> Unit,
    onFailure: (String) -> Unit
) {
    val biometricManager = BiometricManager.from(activity)
    val authenticators = BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL

    // ۱. بررسی درستِ سخت‌افزار (فقط بررسی بیومتریک قوی مثل اثر انگشت)
    when (biometricManager.canAuthenticate(
        authenticators
    )) {
        BiometricManager.BIOMETRIC_SUCCESS -> {
            // همه‌چیز عالی است، ادامه می‌دهیم
        }

        BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
            onFailure("این دستگاه از حسگر اثر انگشت پشتیبانی نمی‌کند")
            return
        }

        BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
            onFailure("سنسور اثر انگشت در دسترس نیست")
            return
        }

        BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
            onFailure("لطفاً ابتدا یک اثر انگشت در تنظیمات گوشی ثبت کنید")
            return
        }

        else -> {
            onFailure("خطای ناشناخته در بیومتریک")
            return
        }
    }

    val executor = ContextCompat.getMainExecutor(activity)
    val biometricPrompt = BiometricPrompt(
        activity, executor,
        object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                onSuccess()
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                // اگر کاربر خودش دکمه‌ی انصراف را زد، نیازی به فرستادن به عنوان خطا نیست
                if (errorCode != BiometricPrompt.ERROR_NEGATIVE_BUTTON &&
                    errorCode != BiometricPrompt.ERROR_USER_CANCELED
                ) {
                    onFailure(errString.toString())
                }
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                // این خطا یعنی انگشت شناسایی شد اما مطابقت نداشت (انگشت اشتباه)
                // معمولاً اینجا onFailure صدا زده نمی‌شود تا کاربر بتواند دوباره تلاش کند
            }
        })

    // تنظیمات ظاهر دیالوگ بیومتریک
    val promptInfo = BiometricPrompt.PromptInfo.Builder()
        .setTitle(title)
        .setAllowedAuthenticators(authenticators)
        .build()

    biometricPrompt.authenticate(promptInfo)
}