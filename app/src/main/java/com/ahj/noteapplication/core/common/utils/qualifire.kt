package com.ahj.noteapplication.core.common.utils

import javax.inject.Qualifier


@Qualifier
annotation class DispatcherIO

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Upsert