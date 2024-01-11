import java.io.FileInputStream
import java.util.*

pluginManagement {
    repositories {
        google()
        mavenCentral()
        mavenLocal()
        gradlePluginPortal()
    }
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    val gradlePropertiesPath = System.getProperty("user.home") + "/.gradle/gradle.properties"
    val globalPropertiesFile = file(gradlePropertiesPath)
    val globalProperties = Properties()
    globalProperties.load(FileInputStream(globalPropertiesFile))

    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        mavenLocal()

        maven {
            url = uri("https://maven.pkg.github.com/ClipMX/*")
            credentials {
                username = globalProperties.getProperty("github.user") ?: System.getenv("USERNAME") ?: ""
                password = globalProperties.getProperty("github.token") ?: System.getenv("TOKEN") ?: ""
            }
        }

        versionCatalogs {
            create("clipLibs") {
                from("com.payclip.blaze:versions-catalog:0.1.3")
            }
        }
    }
}

rootProject.name = "mobile.android.blaze.walkthrough"

include(":app")
 