package com.ahj.noteapplication.feature.authentication.data.repository

import com.ahj.noteapplication.core.common.utils.SafeHelper
import com.ahj.noteapplication.feature.authentication.data.mapper.toDomain
import com.ahj.noteapplication.feature.authentication.domain.model.ChangePassword
import com.ahj.noteapplication.feature.authentication.domain.model.LoginModel
import com.ahj.noteapplication.feature.authentication.domain.model.RegisterModel
import com.ahj.noteapplication.feature.authentication.domain.model.UserModel
import com.ahj.noteapplication.feature.authentication.domain.model.VerifyOtpModel
import com.ahj.noteapplication.feature.authentication.domain.repository.AuthenticationRepository
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.OtpType
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.providers.builtin.OTP
import io.github.jan.supabase.auth.user.UserInfo
import javax.inject.Inject


class AuthenticationRepositoryImpl @Inject constructor(
    private val supabaseAuth: Auth,
    private val safeHelper: SafeHelper
) : AuthenticationRepository {

    override suspend fun login(requestModel: LoginModel): Result<UserModel> =
        safeHelper.authenticationHandler {
            supabaseAuth.signInWith(Email) {
                this.email = requestModel.email
                this.password = requestModel.password
            }
            checkUserData(supabaseAuth.currentUserOrNull())
        }


    override suspend fun register(requestModel: RegisterModel): Result<Boolean> =

        safeHelper.authenticationHandler {
            supabaseAuth.signUpWith(Email) {
                this.email = requestModel.email
                this.password = requestModel.password
            }
            true
        }


    override suspend fun otpVerify(requestModel: VerifyOtpModel): Result<UserModel> =
        safeHelper.authenticationHandler {
            supabaseAuth.verifyEmailOtp(
                type = OtpType.Email.SIGNUP,
                email = requestModel.email,
                token = requestModel.otp
            )

            checkUserData(supabaseAuth.currentUserOrNull())
        }

    override suspend fun resendOtp(email: String): Result<Boolean> =
        safeHelper.authenticationHandler {
            supabaseAuth.resendEmail(
                OtpType.Email.SIGNUP,
                email = email
            )
            true
        }

    override suspend fun sendResetPassOtp(emailAddress: String): Result<Boolean> =
        safeHelper.authenticationHandler {
            supabaseAuth.signInWith(OTP) {
                email = emailAddress
            }
            true
        }

    override suspend fun verifyResetPassOtp(requestModel: VerifyOtpModel): Result<UserModel> =
        safeHelper.authenticationHandler {
            supabaseAuth.verifyEmailOtp(
                OtpType.Email.RECOVERY,
                token = requestModel.otp,
                email = requestModel.email
            )
            checkUserData(supabaseAuth.currentUserOrNull())
        }

    private fun checkUserData(user: UserInfo?): UserModel {
        val user = checkNotNull(user) {
            "حساب کاربری یافت نشد"
        }
        return user.toDomain()
    }


    override suspend fun changePass(changePass: ChangePassword): Result<Unit> =

        safeHelper.authenticationHandler {
            supabaseAuth.updateUser {
                currentPassword = changePass.currentPass
                password = changePass.newPassword
            }
        }

}