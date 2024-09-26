plugins {
    alias(libs.plugins.agp.lib)
    alias(libs.plugins.kotlin)
    id("maven-publish")
}

android {
    namespace = "dev.trindadedev.easyui.components"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.toVersion(libs.versions.android.jvm.get().toInt())
        targetCompatibility = JavaVersion.toVersion(libs.versions.android.jvm.get().toInt())
    }

    kotlinOptions {
        jvmTarget = libs.versions.android.jvm.get()
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            groupId = "com.github.robok-inc"
            artifactId = "easy-components"
            version  = "0.0.1"
            
            from(components.findByName("release"))

            pom {
                name.set("Easy UI Components")
                description.set("Some XML components to help you with development.")
                url.set("https://github.com/trindadev13/easy-ui")
                licenses {
                    license {
                        name.set("GPL 3.0 License")
                        url.set("https://www.gnu.org/licenses/gpl-3.0.pt-br.html")
                    }
                }
                developers {
                    developer {
                        id.set("trindadedev13")
                        name.set("Aquiles Trindade")
                        email.set("devsuay@example.com")
                    }
                }
            }
        }
    }
}
