# ==========================================
# 1. General Attributes & Annotations
# ==========================================
-keepattributes *Annotation*, InnerClasses, Signature, EnclosingMethod
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations, RuntimeVisibleTypeAnnotations

# ==========================================
# 2. Kotlinx Serialization & Navigation
# ==========================================
# نگهداری دقیق کلاس‌های Serializable بدون کل ساختارهای اضافه
-keepclassmembers class * {
    @kotlinx.serialization.Serializable <fields>;
}

-keep,includedescriptorclasses @kotlinx.serialization.Serializable class * { *; }
-keep class * implements kotlinx.serialization.KSerializer
-keepclassmembers class * implements kotlinx.serialization.KSerializer {
    *** INSTANCE;
}

# ==========================================
# 3. Room Database & Domain Models
# ==========================================
# نگهداری انوتیشن‌ها و فیلدهای Entity / DAO
-keepclassmembers class * {
    @androidx.room.* <fields>;
    @androidx.room.* <methods>;
}
-keep @androidx.room.Entity class * { *; }
-keep @androidx.room.Dao interface * { *; }

# ==========================================
# 4. Custom Annotations (DispatcherIO, etc.)
# ==========================================
-keep @interface com.ahj.noteapplication.** { *; }

# ==========================================
# 5. WorkManager & Workers
# ==========================================
-keep class * extends androidx.work.ListenableWorker {
    <init>(android.content.Context, androidx.work.WorkerParameters);
}

# ==========================================
# 6. Third-Party Libraries (Lottie, RichTextEditor, Ktor, Retrofit)
# ==========================================
-keep class com.mohamedrejeb.richeditor.** { *; }
-dontwarn io.ktor.**
-dontwarn retrofit2.**