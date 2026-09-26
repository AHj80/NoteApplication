package com.ahj.noteapplication.feature.authentication.data.mapper

import com.ahj.noteapplication.feature.authentication.domain.model.UserModel
import io.github.jan.supabase.auth.user.UserInfo


fun UserInfo.toDomain(): UserModel =
    UserModel(
        this.id,
        this.email?:""
    )