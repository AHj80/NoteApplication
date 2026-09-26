import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.dagger)
    alias(libs.plugins.kotlinserialization)
}

android {
    namespace = "com.ahj.noteapplication"
    compileSdk = 37


    defaultConfig {
        applicationId = "com.ahj.noteapplication"
        minSdk = 24
        targetSdk = 37
        versionCode = 1
        versionName = "1.1"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val properties = Properties()
        val localPropertiesFile = project.rootProject.file("local.properties")
        if (localPropertiesFile.exists())
            properties.load(localPropertiesFile.inputStream())
        val supabaseUrl = properties.getProperty("SUPABASE_URL") ?: ""
        val supabaseApikey = properties.getProperty("SUPABASE_APIKEY") ?: ""

        buildConfigField("String", "SUPABASE_URL", "\"$supabaseUrl\"")
        buildConfigField("String", "SUPABASE_APIKEY", "\"$supabaseApikey\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    // room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    // kotlin serialization
    implementation(libs.kotlinserialization.retrofit)

    // dagger hillt
    implementation(libs.daggerhillt.android)
    ksp(libs.daggerhillt.compiler)

    // retrofit
    implementation(libs.retrofit2)

    // coroutine
    implementation(libs.coroutine.android)
    implementation(libs.coroutine.core)

    //navigation
    implementation(libs.navigation.compose)
    implementation(libs.hillt.navigation.compose)

    //dataStore
    implementation(libs.datastore)

    // lottie
    implementation(libs.lottie.compose)

    //paging
    implementation(libs.androidx.paging.runtime.ktx)
    implementation(libs.androidx.paging.compose)

    // Icons
    implementation(libs.androidx.compose.material.icons.extended)

    // Coil
    implementation(libs.coil.compose)
    implementation(libs.coil.network)

    // Foundation
    implementation(libs.foundation)

    //security
    implementation(libs.tink.android)

    // ktor
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.android)
    implementation(libs.kotlinserialization)
    implementation(libs.ktor.client.content.negotiation)

    //supabase
    implementation(platform(libs.bom))
    implementation(libs.auth.kt)


}