plugins {
  kotlin("jvm") version "2.0.0"
}

group = "trnqilo"
version = "1.0-SNAPSHOT"

repositories {
  mavenCentral()
}

dependencies {
  implementation("org.apache.poi:poi:+")
  implementation("org.apache.poi:poi-ooxml:+")
}

kotlin {
  jvmToolchain(17)
}
