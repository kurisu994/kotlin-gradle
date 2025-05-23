import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dep.man)
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.spring)
}

group = "cn.hutao.buer"
version = "1.0.0"

springBoot {
    mainClass = "cn.hutao.buer.ApplicationKt"
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

extra["springCloudVersion"] = "2024.0.0"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.springframework.cloud:spring-cloud-starter")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation(libs.ktx.coroutines.core)
    implementation(libs.ktx.coroutines.reactive)
    implementation(libs.ktx.coroutines.reactor)
    implementation(libs.kt.reflect)
    implementation(libs.mybatis.plus)
    implementation(libs.mybatis.plus.generator)
    implementation(libs.velocity.engine)
    implementation(libs.hutool)
    testImplementation("io.projectreactor:reactor-test")
    runtimeOnly("com.h2database:h2")
    runtimeOnly("com.mysql:mysql-connector-j")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

tasks.withType<KotlinCompile> {
    compilerOptions {
        freeCompilerArgs.add("-Xjsr305=strict")
        jvmTarget.set(JvmTarget.JVM_17)
    }
}

tasks.withType<BootJar> {
    archiveFileName.set("${project.name}-${project.version}.jar")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
