import com.adarshr.gradle.testlogger.theme.ThemeType

plugins {
	java
	id("org.springframework.boot") version "3.4.0"
	id("io.spring.dependency-management") version "1.1.6"
	id("com.adarshr.test-logger") version "3.1.0"
	id("com.coditory.integration-test") version "2.2.2"
}

group = "eu.tinylinden"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

extra["springCloudVersion"] = "2024.0.0"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.liquibase:liquibase-core")
	implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
	implementation("com.github.f4b6a3:tsid-creator:5.2.6")
	implementation("org.apache.commons:commons-text:1.12.0")

	compileOnly("org.projectlombok:lombok")

	runtimeOnly("org.postgresql:postgresql")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
	testImplementation("org.assertj:assertj-core")
	testImplementation("net.datafaker:datafaker:2.4.2")
	testImplementation("org.mockito:mockito-core:5.14.2")
	testImplementation("io.rest-assured:rest-assured:5.5.0")
	testImplementation("org.testcontainers:postgresql:1.19.8")

	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	annotationProcessor("org.projectlombok:lombok")
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

testlogger {
	theme = ThemeType.PLAIN
}
