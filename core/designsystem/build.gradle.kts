plugins {
    id("com.inner.second.android.library")
    id("com.inner.second.android.library.compose")
}

android {
    namespace = "com.inner.second.core.designsystem"
}

dependencies {
    implementation(libs.sain)
    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
}