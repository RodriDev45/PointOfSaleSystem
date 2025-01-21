import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.serialization)
}

kotlin {
    jvm("desktop")
    
    sourceSets {
        val desktopMain by getting
        
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)

            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)

            implementation(libs.navigation.compose)

            implementation(project.dependencies.platform(libs.supabase.bom))
            implementation(libs.supabase.postgrest)
            implementation(libs.supabase.auth)
            implementation(libs.supabase.realtime)
            implementation(libs.supabase.storage)
            implementation(libs.supabase.functions)

            implementation(libs.kotlinx.serialization)

            implementation(libs.kamel.image)
            implementation(libs.oracle.database)

            //ktor
            implementation(libs.ktor.core)
            implementation(libs.ktor.cio)
            implementation(libs.ktor.json)
            implementation(libs.ktor.auth)
            implementation(libs.ktor.server.core)
            implementation(libs.ktor.server.netty)
            implementation(libs.ktor.websocket)

            //Jwt
            implementation(libs.java.jwt)
            implementation(libs.jwk.rsa)

            //jbcrypt
            implementation(libs.jbcrypt)

            //mysql
            implementation(libs.mysql.database)

            //Hikari Pool
            implementation(libs.hikari.pool)

        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
            implementation(libs.ktor.client)
        }
    }
}


compose.desktop {
    application {
        mainClass = "org.example.pointofsalesystem.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "org.example.pointofsalesystem"
            packageVersion = "1.0.0"
        }
    }
}
