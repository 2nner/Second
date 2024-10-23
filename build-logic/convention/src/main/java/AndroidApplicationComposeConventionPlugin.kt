import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import com.inner.second.compose.configureAndroidCompose
import com.inner.second.compose.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

class AndroidApplicationComposeConventionPlugin : Plugin<Project>{

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
            }

            extensions.configure<BaseAppModuleExtension> {
                configureAndroidCompose(this)
                defaultConfig.targetSdk = 35
            }
        }
    }
}