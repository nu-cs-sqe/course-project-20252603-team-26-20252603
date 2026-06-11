plugins {
    id("java")
    application
    id("info.solidsoft.pitest") version "1.19.0"
    checkstyle
    jacoco
    id("com.github.spotbugs") version "6.5.4"
}

application {
    mainClass = "ui.Main"
}

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}

group = "nu.csse.sqe"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("com.github.spotbugs:spotbugs-annotations:4.9.8")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.easymock:easymock:5.4.0")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(11)
    }
}

tasks.compileJava {
    options.release = 11
    options.compilerArgs.add("-Xlint:unchecked")
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required = true
        html.required = true
    }
}

pitest {
    junit5PluginVersion.set("1.2.1")
    targetClasses.set(listOf("domain.*", "controller.*", "view.*"))
    targetTests.set(listOf("*Test"))
    threads.set(4)
    outputFormats.set(listOf("HTML", "XML"))
    timestampedReports.set(false)
}
