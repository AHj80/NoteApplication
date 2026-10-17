package com.ahj.noteapplication.core.common.utils

import io.github.jan.supabase.exceptions.RestException
import kotlinx.serialization.SerializationException
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject


class SafeHelper @Inject constructor() {


    suspend fun <T> safeNetwork(request: suspend () -> Response<T>): Result<T> {
        return try {
            val response = request()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body)
                } else {
                    @Suppress("UNCHECKED_CAST")
                    Result.success(Unit as T)
                }
            } else {
                Result.failure(Exception("خطای سرور: ${response.errorBody()?.string()}"))
            }
        } catch (e: Exception) {
            val customException = when (e) {
                is SocketTimeoutException -> Exception("زمان پاسخگویی سرور به پایان رسید.")
                is IOException -> Exception("اتصال اینترنت خود را بررسی کنید.")
                is HttpException -> Exception("خطای شبکه: ${e.code()}")
                is SerializationException -> Exception("خطا در پردازش اطلاعات دریافت شده. ${e.message}")
                else -> Exception("خطای ناشناخته: ${e.message}")
            }
            Result.failure(customException)
        }
    }

    suspend fun <T> authenticationHandler(request: suspend () -> T): Result<T> {
        return try {
            val response = request()
            Result.success(response)
        } catch (e: Exception) {
            val customException = when (e) {
                is IOException -> Exception("اتصال اینترنت خود را بررسی کنید.")
                is RestException -> {
                    when (e.error) {
                        "invalid_credentials", "invalid_grant" ->
                            Exception("ایمیل یا رمز عبور اشتباه است.")

                        "email_not_confirmed" ->
                            Exception("لطفاً ابتدا ایمیل خود را تایید کنید.")

                        "user_already_exists" ->
                            Exception("این ایمیل قبلاً ثبت‌ نام شده است.")

                        else -> Exception("خطا از سمت سرور: ${e.error}")
                    }
                }

                else -> Exception(e.localizedMessage ?: "خطای ناشناخته رخ داده است.")
            }
            Result.failure(customException)
        }
    }

    suspend fun <T> safeDatabase(request: suspend () -> T): Result<T> {
        return try {
            val result = request()
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(Exception("خطای داخلی $e"))
        }
    }

    suspend fun <T> safeAIResponse(request: suspend () -> Response<T>): Result<T> {
        return try {
            val response = request()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(Exception("محتوای پاسخ خالی میباشد"))
                }
            } else {
                Result.failure(Exception("خطای سرور: ${response.errorBody()?.string()}"))
            }
        }catch (e: HttpException) {
            val exception = when(e.code()){
                401 -> "خطای احراز هویت در سرور"
                500 -> "خطای داخلی در سرور یا ارتباط با AI"
                else -> "خطای سرور با کد: ${e.code()}"
            }
            Result.failure(Exception(exception))
        }
        catch (e: IOException){
            Result.failure(Exception("اتصال اینترنت خود را بررسی کنید."))
        }
        catch (e: Exception) {
            Result.failure(Exception("خطای ناشناخته: ${e.localizedMessage}"))
        }
    }
}

