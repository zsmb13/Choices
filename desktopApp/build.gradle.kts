import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    dependencies {
        implementation(projects.shared)

        implementation(compose.desktop.currentOs)
        implementation(libs.kotlinx.coroutinesSwing)

        implementation(compose.components.uiToolingPreview)
    }
}

compose.desktop {
    application {
        mainClass = "co.zsmb.choices.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "co.zsmb.choices"
            packageVersion = "1.0.0"
        }
    }
}