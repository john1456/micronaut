plugins {
    id("org.jetbrains.kotlin.jvm") version "1.7.0"
    id("org.jetbrains.kotlin.plugin.jpa") version "1.7.0"
    id("org.jetbrains.kotlin.kapt") version "1.7.0"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.7.0"
    id("groovy")
    id("idea")
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("org.unbroken-dome.test-sets") version "4.0.0"
    id("io.micronaut.application") version "3.5.1"
}

version = "0.1"
group = "cz.test"

val kotlinVersion = project.properties.get("kotlinVersion")
repositories {
    mavenCentral()
}

micronaut {
    runtime("netty")
    testRuntime("spock2")
    processing {
        incremental(true)
        annotations("cz.test.api.*")
    }
}

kapt {
    arguments {
        arg("micronaut.openapi.views.spec", "swagger-ui.enabled=true,swagger-ui.theme=material,mapping.path=/swagger")
    }
}

dependencies {
    implementation(platform("software.amazon.awssdk:bom:2.17.209"))
    implementation("software.amazon.awssdk:s3")
    implementation("software.amazon.awssdk:ssm")

    annotationProcessor("io.micronaut.data:micronaut-data-processor")
    kapt("io.micronaut.openapi:micronaut-openapi")
    kapt("io.micronaut:micronaut-http-validation")
    implementation("org.ff4j:ff4j-core:1.8.13")
    implementation("io.swagger.core.v3:swagger-annotations")
    implementation("com.nimbusds:nimbus-jose-jwt:9.22")
    implementation("io.micronaut:micronaut-http-client")
    implementation("io.micronaut:micronaut-management")
    implementation("io.micronaut:micronaut-runtime")
    implementation("io.micronaut.aws:micronaut-aws-parameter-store")
    implementation("io.micronaut.cache:micronaut-cache-caffeine")
    implementation("io.micronaut.data:micronaut-data-hibernate-jpa")
    implementation("com.vladmihalcea:hibernate-types-55:2.16.2")
    implementation("io.micronaut.flyway:micronaut-flyway")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    implementation("io.micronaut.reactor:micronaut-reactor")
    implementation("io.micronaut.security:micronaut-security-jwt")
    implementation("io.micronaut.security:micronaut-security-oauth2")
    implementation("org.jetbrains.kotlin:kotlin-reflect:${kotlinVersion}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${kotlinVersion}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.6.2")
    implementation("org.keycloak:keycloak-admin-client:18.0.2")
    implementation("com.google.code.gson:gson:2.8.6")
    implementation("org.postgresql:postgresql")
    implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")
    implementation("io.micronaut:micronaut-validation")
    implementation("org.apache.pdfbox:pdfbox:2.0.26")
    implementation("com.ToxicBakery.library.bcrypt:bcrypt:1.0.9")
    implementation("io.micronaut.email:micronaut-email-javamail")
    implementation("io.micronaut.email:micronaut-email-template")
    implementation("net.coobird:thumbnailator:0.4.1")
    implementation("io.micronaut.views:micronaut-views-handlebars")
    runtimeOnly("io.micronaut.sql:micronaut-jdbc-hikari")
    runtimeOnly("ch.qos.logback:logback-classic")
    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")
    testImplementation("org.testcontainers:postgresql:1.17.1")
    testImplementation("com.github.dasniko:testcontainers-keycloak:2.2.2")
    testImplementation("org.testcontainers:localstack:1.17.1")
    testImplementation("org.testcontainers:testcontainers:1.17.1")
    testImplementation("org.testcontainers:spock:1.17.1")
    testImplementation("io.projectreactor:reactor-test:3.4.18")
    testImplementation("com.amazonaws:aws-java-sdk-core:1.12.220") // we need this dependency until testcontainers:localstack gets the full support for aws sdk v2 only
    testImplementation("io.kotest:kotest-runner-junit5:5.4.0")
    testImplementation("io.kotest:kotest-assertions-core:5.4.0")
    testImplementation("io.mockk:mockk:1.12.4")

    kaptTest("io.micronaut:micronaut-inject-java")
    testImplementation("io.micronaut.test:micronaut-test-kotest5:3.5.0")
    testImplementation("io.kotest:kotest-runner-junit5-jvm:5.4.0")

}

testSets {
    create("integrationTest")
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}


idea {
    module {
        sourceDirs.remove(file("src/integrationTest/groovy"))
        sourceDirs.remove(file("src/integrationTest/kotlin"))
        testSourceDirs.add(file("src/integrationTest/groovy"))
        testSourceDirs.add(file("src/integrationTest/kotlin"))
    }
}

application {
    mainClass.set("cz.test.api.Application")
}
java {
    sourceCompatibility = JavaVersion.toVersion("17")
}

tasks {
    compileKotlin {
        kotlinOptions {
            jvmTarget = "11"
        }
    }
    compileTestKotlin {
        kotlinOptions {
            jvmTarget = "17"
        }
    }
//    dockerfile {
//        baseImage.set("amazoncorretto:17")
//    }
}
