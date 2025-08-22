import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

plugins {
    kotlin("jvm") version "1.9.23" apply false
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")

    repositories {
        mavenCentral()
    }

    dependencies {
        "testImplementation"(kotlin("test"))
        "testImplementation"("org.assertj:assertj-core:3.26.3")
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    configure<KotlinJvmProjectExtension> {
        jvmToolchain(8)
    }
}