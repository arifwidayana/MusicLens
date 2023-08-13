import java.io.FileInputStream
import java.util.Properties

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlinKapt)
    alias(libs.plugins.ktlintAndroid)
}

val keyProperties = Properties()
keyProperties.load(FileInputStream(File("core.properties")))

android {
    namespace = "com.arifwidayana.musiclens"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.arifwidayana.musiclens"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "BASE_URL", keyProperties.getProperty("BASE_URL"))
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
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    packaging {
        resources.excludes.add("META-INF/*")
    }
}

dependencies {
    // Lifecycle
    implementation(libs.lifecycle.runtime)
    implementation(libs.lifecycle.viewmodel)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.retrofit.adapter.rxjava3)

    // Coroutine
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)

    // Datastore
    implementation(libs.datastore)

    // Gson
    implementation(libs.gson)

    // Koin
    implementation(libs.koin)

    // Paging
    implementation(libs.paging.runtime)

    // Coil
    implementation(libs.coil)

    // Mockk
    testImplementation(libs.mockk)

    // Mockito
    testImplementation(libs.mockito.core)
    androidTestImplementation(libs.mockito.android)

    // Chucker
    debugImplementation(libs.chucker)
    releaseImplementation(libs.chucker.no.op)

    // Room
    implementation(libs.room.ktx)
    implementation(libs.room.runtime)
    kapt(libs.room.compiler)

    // Timber
    implementation(libs.timber)

    // RxJava3
    implementation(libs.rxjava)
    implementation(libs.rxandroid)

    // Dagger2
    implementation(libs.dagger)
    implementation(libs.dagger.android.support)
    kapt(libs.dagger.compiler)
    kapt(libs.dagger.android.processor)

    // Default
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}