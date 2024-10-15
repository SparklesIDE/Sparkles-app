
plugins {
    id("com.android.application")
    
}

android {
    namespace = "com.sparkleside"
    compileSdk = 34
    
    defaultConfig {
        applicationId = "com.sparkleside"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        
        vectorDrawables { 
            useSupportLibrary = true
        }
    }
    
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    buildFeatures {
        viewBinding = true
        
    }
    
}

dependencies {


    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation(platform("io.github.Rosemoe.sora-editor:bom:0.23.4"))
    implementation("io.github.Rosemoe.sora-editor:editor:0.23.4")
  //  implementation(dir: "libs", include: ["*.jar", "*.aar"])
    
   // implementation("com.github.Ruan625Br:FilePickerSphere:1.0.0")
}
