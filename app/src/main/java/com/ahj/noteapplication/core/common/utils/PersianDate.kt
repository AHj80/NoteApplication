package com.ahj.noteapplication.core.common.utils

import java.util.Calendar
import java.util.GregorianCalendar
import javax.inject.Inject

class PersianDate @Inject constructor() {

    var strWeekDay = ""
    var strMonth = ""
    var day = 0
    var month = 0
    var year = 0
    var hour = 0
    var min = 0
    var second = 0

    // تبدیل تاریخ با استفاده از Timestamp دلخواه (یا زمان فعلی)
    fun fromTimestamp(timestamp: Long = System.currentTimeMillis()): PersianDate {
        val calendar = GregorianCalendar().apply {
            timeInMillis = timestamp
        }
        setDateCalendar(calendar)
        return this
    }

    fun getCustomDate(): String = "$day $strMonth $year".toPersianDigits()

    private fun setDateCalendar(calendar: GregorianCalendar) {
        val ld: Int

        hour = calendar.get(Calendar.HOUR_OF_DAY)
        min = calendar.get(Calendar.MINUTE)
        second = calendar.get(Calendar.SECOND)

        val persianYear = calendar.get(Calendar.YEAR)
        val persianMonth = calendar.get(Calendar.MONTH) + 1
        val persianDate = calendar.get(Calendar.DATE)
        val weekDay = calendar.get(Calendar.DAY_OF_WEEK)

        val buf1 = intArrayOf(0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334)
        val buf2 = intArrayOf(0, 31, 60, 91, 121, 152, 182, 213, 244, 274, 305, 335)

        if (persianYear % 4 != 0) {
            this.day = buf1[persianMonth - 1] + persianDate
            if (this.day > 79) {
                this.day -= 79
                if (this.day <= 186) {
                    when (this.day % 31) {
                        0 -> { month = this.day / 31; this.day = 31 }
                        else -> { month = this.day / 31 + 1; this.day %= 31 }
                    }
                    year = persianYear - 621
                } else {
                    this.day -= 186
                    when (this.day % 30) {
                        0 -> { month = this.day / 30 + 6; this.day = 30 }
                        else -> { month = this.day / 30 + 7; this.day %= 30 }
                    }
                    year = persianYear - 621
                }
            } else {
                ld = if (persianYear > 1996 && persianYear % 4 == 1) 11 else 10
                this.day += ld
                when (this.day % 30) {
                    0 -> { month = this.day / 30 + 9; this.day = 30 }
                    else -> { month = this.day / 30 + 10; this.day %= 30 }
                }
                year = persianYear - 622
            }
        } else {
            this.day = buf2[persianMonth - 1] + persianDate
            ld = if (persianYear >= 1996) 79 else 80
            if (this.day > ld) {
                this.day -= ld
                if (this.day <= 186) {
                    when (this.day % 31) {
                        0 -> { month = this.day / 31; this.day = 31 }
                        else -> { month = this.day / 31 + 1; this.day %= 31 }
                    }
                    year = persianYear - 621
                } else {
                    this.day -= 186
                    when (this.day % 30) {
                        0 -> { month = this.day / 30 + 6; this.day = 30 }
                        else -> { month = this.day / 30 + 7; this.day %= 30 }
                    }
                    year = persianYear - 621
                }
            } else {
                this.day += 10
                when (this.day % 30) {
                    0 -> { month = this.day / 30 + 9; this.day = 30 }
                    else -> { month = this.day / 30 + 10; this.day %= 30 }
                }
                year = persianYear - 622
            }
        }

        strMonth = when (month) {
            1 -> "فروردین"; 2 -> "اردیبهشت"; 3 -> "خرداد"
            4 -> "تیر"; 5 -> "مرداد"; 6 -> "شهریور"
            7 -> "مهر"; 8 -> "آبان"; 9 -> "آذر"
            10 -> "دی"; 11 -> "بهمن"; 12 -> "اسفند"
            else -> ""
        }

        strWeekDay = when (weekDay) {
            1 -> "یکشنبه"; 2 -> "دوشنبه"; 3 -> "سه شنبه"
            4 -> "چهارشنبه"; 5 -> "پنج شنبه"; 6 -> "جمعه"
            7 -> "شنبه"; else -> ""
        }
    }

    // گرفتن تاریخ عددی (مثال: 1403/06/02)
    fun getShortDate(): String {
        val m = if (month < 10) "0$month" else "$month"
        val d = if (day < 10) "0$day" else "$day"
        return "$year/$m/$d".toPersianDigits()
    }

    // گرفتن تاریخ متنی کامل (مثال: جمعه ۲ شهریور ۱۴۰۳)
    fun getFullDate(): String {
        return "$strWeekDay $day $strMonth $year".toPersianDigits()
    }

    // تبدیل اعداد انگلیسی به فارسی
    private fun String.toPersianDigits(): String {
        val persianNumbers = arrayOf('۰', '۱', '۲', '۳', '۴', '۵', '۶', '۷', '۸', '۹')
        return this.map { char ->
            if (char.isDigit()) persianNumbers[char.toString().toInt()] else char
        }.joinToString("")
    }
}