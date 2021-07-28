import org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
import org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED
import org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED
import org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED
import org.gradle.api.tasks.testing.logging.TestLogEvent.STANDARD_ERROR
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

plugins {
    "kotlin"
    id("org.springframework.boot") version "2.4.3"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("org.jlleitschuh.gradle.ktlint") version "9.2.1"
    `maven-publish`
    kotlin("jvm") version "1.3.71"
    kotlin("plugin.spring") version "1.3.71"
}

group = "org.dummy-org-gsd-days"
version = "0.0.11"

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
            freeCompilerArgs = listOf("-Xjsr305=strict")
        }
    }

    test {
        useJUnitPlatform {
            excludeTags("integration-test")
        }
        enableAssertions = true
        testLogging {
            exceptionFormat = FULL
            events = setOf(FAILED, PASSED, SKIPPED, STANDARD_ERROR)
        }
    }

    val integrationTest by registering(Test::class) {
        group = "verification"
        description = "Execute integration tests."
        useJUnitPlatform {
            includeTags("integration-test")
        }
        enableAssertions = true
        testLogging {
            exceptionFormat = FULL
            events = setOf(FAILED, PASSED, SKIPPED, STANDARD_ERROR)
        }
    }

    ktlint {
        verbose.set(true)
        version.set("0.36.0")
        kotlinScriptAdditionalPaths { include(fileTree(".")) }
        filter { exclude("build/**") }
        reporters {
            reporter(ReporterType.PLAIN)
            reporter(ReporterType.CHECKSTYLE)
        }
    }
}

repositories {
    mavenCentral()
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/dummy-org-gsd-days/spring-boot-example")
            credentials {
                authentication
                username = "dummy-org-gsd-days"
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
    publications {
        create<MavenPublication>("spring-boot-example") {
            from(components["java"])
        }
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.postgresql:postgresql:42.2.19")
    compile("org.flywaydb:flyway-core:7.11.4")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit"))

    testImplementation("org.junit.jupiter:junit-jupiter:5.7.2")
    testImplementation("org.assertj:assertj-core:3.20.2")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")

    testCompile("org.testcontainers:junit-jupiter:1.16.0")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.5.2")
}
