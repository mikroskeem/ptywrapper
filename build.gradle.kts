import org.gradle.api.tasks.testing.logging.TestExceptionFormat

plugins {
    `java-library`
    id("net.minecrell.licenser") version "0.3"
    `maven-publish`
}

group = "eu.mikroskeem"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenLocal()
    mavenCentral()
}

val jnaVersion = "4.5.1"
val jbAnnotationsVersion = "15.0"

val junitVersion = "5.1.0"

dependencies {
    implementation("net.java.dev.jna:jna:$jnaVersion")
    //implementation("net.java.dev.jna:jna-platform:$jnaVersion")
    implementation("org.jetbrains:annotations:$jbAnnotationsVersion")

    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testRuntime("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
}

val test by tasks.getting(Test::class) {
    useJUnitPlatform()

    systemProperty("jna.debug_load", "true")

    // Show output
    testLogging {
        showStandardStreams = true
        exceptionFormat = TestExceptionFormat.FULL
    }

    // Verbose
    beforeTest(closureOf<Any> { logger.lifecycle("Running test: $this") })
}