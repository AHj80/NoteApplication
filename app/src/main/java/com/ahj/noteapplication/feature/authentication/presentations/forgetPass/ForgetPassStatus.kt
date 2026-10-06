package com.ahj.noteapplication.feature.authentication.presentations.forgetPass

enum class ForgetPassStatus {

    IDELE,
    LOADING,
    // EMAIL SENDING OTP
    EMAIL_SUCCESS,
    EMAIL_ERROR,
    // VERIFY OTP
    VERIFY_SUCCESS,
    VERIFY_ERROR,

}