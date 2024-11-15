plugins {
    id("com.inner.second.android.library")
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt.plugin)
}

android {
    namespace = "com.inner.second.core.data"
}

dependencies {
    api(projects.core.model)
    implementation(projects.core.designsystem)

    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.itext.core)
    implementation(libs.itext.html2pdf)
}