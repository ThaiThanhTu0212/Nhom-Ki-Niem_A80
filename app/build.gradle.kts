plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.thiennguyen"
    compileSdk = 34 // Đổi về 34 (Android 14) cho ổn định. 36 là bản Preview rất mới có thể gây lỗi.

    defaultConfig {
        applicationId = "com.example.thiennguyen"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        // Đã sửa: Chỉ giữ lại Java 11 (Xóa đoạn trùng lặp Java 1.8)
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

dependencies {

    // Firebase - Cập nhật BoM theo hướng dẫn mới nhất
    implementation(platform("com.google.firebase:firebase-bom:34.6.0"))
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-firestore")


    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Các thư viện hệ thống khác (RecyclerView, CardView...)
    // Nếu bạn dùng libs.versions.toml thì giữ nguyên, nếu không hãy thay bằng số phiên bản cụ thể
    implementation("androidx.activity:activity:1.8.0")
    implementation("androidx.fragment:fragment:1.6.2")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("androidx.viewpager2:viewpager2:1.0.0")

    // --- XỬ LÝ ẢNH (IMAGE PROCESSING) ---
    // CircleImageView (Ảnh tròn)
    implementation("de.hdodenhof:circleimageview:3.1.0")
    // RoundedImageView (Ảnh bo góc)
    implementation("com.makeramen:roundedimageview:2.3.0")

    // GLIDE (Đã sửa: Chỉ dùng phiên bản mới nhất 4.16.0)
    implementation("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.16.0")

    // --- MẠNG (NETWORKING) ---
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // --- TESTING ---
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}