plugins {
    id("com.android.library")
}

android {
    namespace = "com.termux.view"
    compileSdk = 34

    defaultConfig {
        minSdk = 21
        
    }

    // تنظیم targetSdk در testOptions
    testOptions {
        targetSdk = 34
    }

    // تنظیم targetSdk در lint
    lint {
        targetSdk = 34
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
dependencies{
  implementation("androidx.appcompat:appcompat:1.6.1")

}