plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    // hilt
    // kapt
    id ("kotlin-kapt")
}

android {
    namespace = "com.example.newsapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.newsapp"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            buildConfigField("String","BASE_URL","\"https://newsapi.org/v2/\"")
            buildConfigField("String","API_KEY","\"125a2fcd18074fe99dc2aeefb779b466\"")
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String","BASE_URL","\"https://newsapi.org/v2/\"")
            buildConfigField("String","API_KEY","\"125a2fcd18074fe99dc2aeefb779b466\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    kotlin {
        jvmToolchain {
            // Set the Java version you want to use for Kotlin tasks
            languageVersion.set(JavaLanguageVersion.of(17))
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.0")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation(project(":domain"))
    implementation(project(":Base"))
    implementation(project(":data"))
    implementation(project(":di"))
    implementation("com.google.dagger:hilt-android:2.44")
    kapt("com.google.dagger:hilt-android-compiler:2.44")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.0")
    implementation("androidx.fragment:fragment-ktx:1.5.7")
    implementation("androidx.activity:activity-ktx:1.7.1")
    implementation("androidx.navigation:navigation-runtime:2.7.0")
    implementation("com.github.bumptech.glide:glide:4.14.2")
    kapt ("com.github.bumptech.glide:compiler:4.12.0")
    // Turbine is a small testing library for kotlinx.coroutines
    testImplementation("app.cash.turbine:turbine:0.7.0")
    // mockito
    testImplementation("org.mockito:mockito-inline:2.21.0")
    testImplementation("org.mockito:mockito-core:4.0.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.1")

    // pagination
    implementation("androidx.paging:paging-runtime-ktx:3.2.0")
}
kapt {
    correctErrorTypes = true
}