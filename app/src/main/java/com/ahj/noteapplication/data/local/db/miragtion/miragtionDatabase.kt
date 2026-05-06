package com.ahj.noteapplication.data.local.db.miragtion

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


val MIGRATION_1_2 = object: Migration(1,2){
    override fun migrate(db: SupportSQLiteDatabase){
        db.execSQL("ALTER TABLE NoteEntity ADD COLUMN fontSize INTEGER NOT NULL DEFAULT 16")
                                /*نام انتیتی*/        /*فونت سایز نام ستون که باید با نام داخل ورودی انتیتی یکی باشد*/
    }
}

// اگر در دیتابیس نیاز داشتیم که بعد ساخت اولیه ی دیتابیس یک ستون مثلا رنگ یا مانند بالا فونت سایز ذخیره سازی و ایجاد شود باید از میگریشن استافده کنیم کارایی ان این است که باعث
// میشود فایل دیتابیس از ورژن یک به ورژن بعدی انتقال پیدا کند بدون ان که داده های کاربر از دست برود و یا نیاز به حذف برنامه و نصب مجدد داشته باشد -
// نیاز است ابتدا ورژن را در مای دیتابیس به نسخه جدید تر ارتقا بدیم - بعد از ان باید در فایل جدا گانه ای متغیری ایجاد کنیم تحت عنوان میگریشن که نسخه ی انتقالی را مانند بالا در نام ان
// ذکر میکنیم بعد از ان میگوییم برابر با ابجکتی است از نوع میگریشن و اینجا هم نسخه ی انتقالی را قرار میدهیم و بعد باید در بلاک میگریشن اورراید کنیم فان میگریت که در ورودی میگوییم
// یک دی بی از نوع ساپورت ای کیو لایت دیتابیس بگیر و بعد در ورودی ان در نهایت مانند بالا دستور را وارد میکنیمکه به این دتسور عمل آلتر تیبل میگویند
// ساختار این دستور به این شکل میباشد:
/*
* جمع‌بندی ساختار دستور:
نام ستون: color
نوع داده: INTEGER (برای اعداد) یا TEXT (برای متن)
اجبار: NOT NULL (یعنی این ستون نمی‌تواند خالی باشد)
مقدار پیش‌فرض: DEFAULT [Value] (مقداری که برای نوت‌های قدیمی که قبلاً ذخیره شده بودند، در نظر گرفته می‌شود).*/

// نکته مهم دیگر این است که باید بعد از این کار اخرین نسخه ای که بر اساس متغیر تعریف کردیم به ماژول خودمان در قسمت ادد میگریشن پاس دهیم:
/* @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MyDatabase =
        Room.databaseBuilder(
            context = context,
            name = MyDatabase.NOTE_DB,
            klass = MyDatabase::class.java
        )
            .addMigrations(MIGRATION_1_2) این قسمت
            .build()*/