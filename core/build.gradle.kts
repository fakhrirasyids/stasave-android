plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.ksp)
}

apply(from = "../shared_dependencies.gradle")

android {
    namespace = "com.fakhrirasyids.stasave.core"
    compileSdk = 35

    defaultConfig {
        minSdk = 26

        buildConfigField("String", "DATABASE_NAME", "\"Stasave.DB\"")
        buildConfigField("String", "STASAVE_DATABASE_KEY", "\"stasave_key_db_226\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
    buildFeatures {
        buildConfig = true
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
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    // DataStore
    implementation(libs.androidx.datastore.preferences)

    // Room
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.paging)
    implementation(libs.androidx.paging.runtime.ktx)
    annotationProcessor(libs.androidx.room.compiler)

    // SQLCipher
    implementation(libs.android.database.sqlcipher)
    implementation(libs.androidx.sqlite.ktx)

    // DocumentFile
    implementation (libs.androidx.documentfile)
}