# 📝 Note Application (V2)
#
<div align="right">
  <img src="https://img.shields.io/badge/Platform-Android-green?logo=android" alt="Android" />
  <img src="https://img.shields.io/badge/Language-Kotlin-purple?logo=kotlin" alt="Kotlin" />
  <img src="https://img.shields.io/badge/Architecture-Clean%20%2B%20MVVM%2F%20MVI-blue" alt="Architecture" />
  <img src="https://img.shields.io/badge/UI-Jetpack%20Compose-darkgreen?logo=jetpackcompose" alt="UI" />
</div>


📜 Overview

نسخه دوم اپلیکیشن دفترچه یادداشت فارسی که در این نسخه قابلیت احراز هویت و رویکرد Offline First و استفاده از AI و همگام سازی یادداشت ها با استفاده از WorkManager پیاده سازی شده است.

---

# 📱 Screenshots
#

|                                                           صفحه ورود                                                            |                                                        لیست یادداشت ها                                                         |                                                         ایجاد یادداشت                                                          |
|:------------------------------------------------------------------------------------------------------------------------------:|:------------------------------------------------------------------------------------------------------------------------------:|:------------------------------------------------------------------------------------------------------------------------------:|
| <img width="196" height="462" alt="1" src="https://github.com/user-attachments/assets/34edbf49-bb76-478f-8a7a-f040805d7bc0" /> | <img width="196" height="462" alt="2" src="https://github.com/user-attachments/assets/9f244a9c-ae70-4c8a-815c-9db45b79e8fb" /> | <img width="185" height="439" alt="3" src="https://github.com/user-attachments/assets/6eccf805-2c5e-4d7c-96c5-e62023326d54" /> |

Download Apk(coming soon)

---

# 🚀 Features


- احراز هویت کاربران (Authentication)
- فرآیند اختصاصی تایید رمز یک‌بارمصرف (OTP Flow)
- استفاده از AI Agent
- ابزار های نوشتار متن
- رابط کاربری مدرن با Jetpack Compose
- طراحی استاندارد بر پایه Glass Morphism
- مدیریت وضعیت (State Management) با StateFlow
- پشتیبانی از ذخیره‌سازی و داده‌های آفلاین
- رابط کاربری واکنش‌گرا (Responsive UI)
- پیاده‌سازی بر پایه Clean Architecture
- الگوی معماری MVVM

---

# 🛠 Tech Stack

### English

- Kotlin
- Jetpack Compose
- Material 3
- Supabase
- MVVM
- Clean Architecture
- Hilt
- Retrofit
- Kotlinx Serialization
- Coroutines
- StateFlow
- Navigation Component
- Work Manager


---

# 🏛 Architecture


این پروژه بر اساس اصول **Clean Architecture** و الگوی **MVVM** پیاده‌سازی شده است:

```text
Presentation ──► Domain ──► Data
```

این تفکیک لایه‌ای باعث جداسازی کامل منطق برنامه (Business Logic) از لایه رابط کاربری (UI) شده و تست‌پذیری و توسعه‌پذیری پروژه را به حداکثر می‌رساند.

---

# 📂 Project Structure

###
ساختار پروژه به صورت **Feature-based** (ویژگی‌محور) طراحی شده است؛ به این صورت که هر ویژگی به صورت مستقل شامل ۳ لایه اختصاصی زیر می‌باشد:

```text
feature/
├── presentation/  # Composables, ViewModels & UI States & ScreenStatus
├── domain/        # Use Cases, Domain Models & Repository Interfaces
└── data/          # DTOs, API Services & Repository Implementations
```

---



# 📄 License


این پروژه نسخه دوم از دفترچه یادداشت میباشد که در دست توسعه هست و بزودی نسخه ی ریلیز منتشر میگردد

---



# 👨‍💻 Contact

- **GitHub:** [AHj80](https://github.com/AHj80)
- **Telegram ID:** [@AHj80]
- **Email:** amirhosseinjalili00@gmail.com
