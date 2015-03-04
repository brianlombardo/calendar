plugins {
  alias(libs.plugins.jvm)
  `java-library`
  kotlin("plugin.serialization") version "2.0.0"
}

repositories {
  mavenCentral()
}

dependencies {
  api("com.jcraft:jsch:0.1.55")
  api("com.squareup.okhttp3:okhttp:4.12.0")
  testImplementation("org.jetbrains.kotlinx:kotlinx-serialization-json:+")
  testImplementation("io.mockk:mockk:+")
}

testing {
  suites {
    val test by getting(JvmTestSuite::class) {
      useKotlinTest("1.9.22")
    }
  }
}


java {
  toolchain {
    languageVersion = JavaLanguageVersion.of(17)
  }
}
