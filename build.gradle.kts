import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    kotlin("jvm") version "1.8.21"
    id("io.ktor.plugin") version "2.3.2"
    id("jacoco")
    kotlin("plugin.serialization") version "1.7.10"
    application
}

group = "org.gradle"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}


dependencies {
    implementation("io.ktor:ktor-client-core")
    implementation("io.ktor:ktor-server-netty")
    implementation("io.ktor:ktor-client-resources")
    implementation("io.ktor:ktor-client-okhttp")
    implementation("io.ktor:ktor-client-cio")
    implementation("io.ktor:ktor-serialization-kotlinx-json")
    implementation("io.ktor:ktor-client-content-negotiation")
    testImplementation("io.ktor:ktor-client-mock")
    testImplementation("io.mockk:mockk:1.9.3")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()

    testLogging {
        events = setOf(TestLogEvent.FAILED, TestLogEvent.PASSED,
            TestLogEvent.STARTED,
            TestLogEvent.SKIPPED,
            TestLogEvent.STANDARD_ERROR,
            TestLogEvent.STANDARD_OUT)
        showStandardStreams = true
        showCauses = true
        showExceptions = true
        showStackTraces = true
    }

    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    reports {
        xml.required.set(false)
        csv.required.set(false)
        html.outputLocation.set(layout.buildDirectory.dir("jacocoHtml"))
    }
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.withType<Test> {
    testLogging {
        events = setOf(TestLogEvent.FAILED, TestLogEvent.PASSED)
        showStandardStreams = true
        showCauses = true
        showExceptions = true
        showStackTraces = true

        debug {
            events = setOf(
                TestLogEvent.STARTED,
                TestLogEvent.FAILED,
                TestLogEvent.PASSED,
                TestLogEvent.SKIPPED,
                TestLogEvent.STANDARD_ERROR,
                TestLogEvent.STANDARD_OUT
            )

            exceptionFormat = TestExceptionFormat.FULL
        }
    }
}

application {
    mainClass.set("MainKt")
}

tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            limit {
                minimum = "0.7".toBigDecimal()
            }
        }

        rule {
            isEnabled = false
            element = "CLASS"
            includes = listOf("org.gradle.*")

            limit {
                counter = "LINE"
                value = "TOTALCOUNT"
                maximum = "0.3".toBigDecimal()
            }
        }
    }
}

