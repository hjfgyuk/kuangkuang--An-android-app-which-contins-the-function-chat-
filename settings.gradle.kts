pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
        buildscript{
            repositories{
                maven("https://maven.aliyun.com/repository/public")
                maven("https://maven.aliyun.com/repository/goole")
                maven("https://maven.aliyun.com/repository/gradle-plugin")
                maven("https://maven.aliyun.com/repository/jcenter")
                maven("https://developer.huawei.com/repo/")
                maven("")
                google()
                mavenCentral()
            }
            dependencies{
                classpath("com.android.tools.build:gradle-settings:8.12.0")
            }
        }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "哐哐app"
include(":app")
 