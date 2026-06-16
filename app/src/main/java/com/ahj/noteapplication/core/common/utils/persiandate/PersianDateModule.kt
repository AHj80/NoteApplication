package com.ahj.noteapplication.core.common.utils.persiandate

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.Date
import java.util.GregorianCalendar
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersianDateModule {


    @Provides
    @Singleton
    fun provideDate(): Date = Date()

    @Provides
    @Singleton
    fun provideGregorianCalendar(date: Date): GregorianCalendar =
        GregorianCalendar().apply {
            time = date
        }

}