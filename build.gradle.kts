import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.2.2"
	id("io.spring.dependency-management") version "1.1.4"
	kotlin("jvm") version "1.9.22"
	kotlin("plugin.spring") version "1.9.22"
}

group = "com.danilo.project.taskmanager"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-hateoas")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")

	//database
	runtimeOnly("com.mysql:mysql-connector-j")

	//flyway
	implementation("org.flywaydb:flyway-core")
	implementation("org.flywaydb:flyway-mysql")

	//Spring Security
	implementation("org.springframework.boot:spring-boot-starter-security")

	//validação dos campos
	implementation("org.springframework.boot:spring-boot-starter-validation")

	//Json Web Token
	implementation("io.jsonwebtoken:jjwt:0.9.1")

	/**
	 * Fix Erro
	 * path [] threw exception
	 * [Handler dispatch failed: java.lang.NoClassDefFoundError: javax/xml/bind/DatatypeConverter]
	 * with root cause
	 */
	implementation("javax.xml.bind:jaxb-api:2.3.1")

	developmentOnly("org.springframework.boot:spring-boot-devtools")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "21"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
