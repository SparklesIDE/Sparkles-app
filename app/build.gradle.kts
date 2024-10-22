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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
        isCoreLibraryDesugaringEnabled = true
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

    signingConfigs {
        getByName("debug") {
            storeFile = file(layout.buildDirectory.dir("../testkey.keystore"))
            storePassword = "testkey"
            keyAlias = "testkey"
            keyPassword = "testkey"
        }
    }
}

dependencies {
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation(platform("io.github.Rosemoe.sora-editor:bom:0.23.4"))
    implementation("io.github.Rosemoe.sora-editor:editor:0.23.4")
    implementation("com.github.bumptech.glide:glide:4.16.0")
    implementation("androidx.activity:activity:1.6.0-alpha05")
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar", "*.aar"))))
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.0.9")
    implementation(project(":terminalview"))
    implementation(project(":peekandpop"))
    implementation("com.blankj:utilcodex:1.31.1")
}
