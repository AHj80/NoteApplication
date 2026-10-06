package com.ahj.noteapplication.feature.authentication.domain.repository

import com.ahj.noteapplication.feature.authentication.domain.model.LoginModel
import com.ahj.noteapplication.feature.authentication.domain.model.UserModel
import com.ahj.noteapplication.feature.authentication.domain.model.RegisterModel
import com.ahj.noteapplication.feature.authentication.domain.model.VerifyOtpModel


interface AuthenticationRepository {

    suspend fun login(requestModel: LoginModel): Result<UserModel>

    suspend fun register(requestModel: RegisterModel): Result<Boolean>

    suspend fun otpVerify(requestModel: VerifyOtpModel): Result<UserModel>

    suspend fun resendOtp(email: String): Result<Boolean>

    suspend fun sendResetPassOtp(emailAddress: String): Result<Boolean>

    suspend fun verifyResetPassOtp(requestModel : VerifyOtpModel): Result<UserModel>

}