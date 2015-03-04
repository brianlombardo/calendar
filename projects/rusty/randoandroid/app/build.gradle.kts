plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
//  id("org.mozilla.rust-android-gradle.rust-android")
}

android {
  namespace = "trnqilo.rustyrando"
  compileSdk = 35

  defaultConfig {
    applicationId = "trnqilo.rustyrando"
    minSdk = 24
    targetSdk = 34
    versionCode = 1
    versionName = "1.0"
//    ndk {
//      abiFilters += listOf("arm64-v8a")
//    }
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }
  kotlinOptions {
    jvmTarget = "11"
  }
//  sourceSets {
//    getByName("main") {
//      jniLibs.srcDirs("src/main/jniLibs")
//    }
//  }
}

//cargo {
//  module = "../lib"
//  libname = "rustyrando"
//  targets = listOf("arm64")
//}

dependencies {
  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.appcompat)
  implementation(libs.material)
  implementation(libs.androidx.activity)
  implementation(libs.androidx.constraintlayout)
  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)
}