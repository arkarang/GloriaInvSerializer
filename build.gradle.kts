plugins {
    id("java")
    kotlin("jvm") version "1.8.20"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("io.papermc.paperweight.userdev") version "1.5.5" apply false
    id("co.uzzu.dotenv.gradle") version "2.0.0"
    id("maven-publish")
}

group = "kr.mcgloria"
version = "1.0-SNAPSHOT"

group = rootProject.group
version = rootProject.version

allprojects {
    apply {
        plugin("java")
        plugin("kotlin")
        plugin("com.github.johnrengelman.shadow")
        plugin("maven-publish")
    }

    repositories {
        mavenLocal()
        maven {
            url = uri("https://repo.papermc.io/repository/maven-public/")
        }
    }

    dependencies {
        compileOnly("io.papermc.paper:paper-api:1.19.4-R0.1-SNAPSHOT")
    }
}

dependencies {

    implementation(project(mapOf("path" to ":common")))
    implementation(project(mapOf("path" to ":nms-1194")))
    implementation(project(mapOf("path" to ":nms-1182")))
    implementation("com.github.luben:zstd-jni:1.5.2-5")
    //implementation("kr.mcgloria:common:1.0-SNAPSHOT")
    //implementation("kr.mcgloria:nms-1182:1.0-SNAPSHOT")
    //implementation("kr.mcgloria:nms-1194:1.0-SNAPSHOT")
}

repositories {
    mavenLocal()
    mavenCentral()
    maven {
        name = "minepalm-snapshots"
        url = uri("https://repo.minepalm.com/repository/maven-snapshots")
        credentials {
            username = project.properties["myNexusUsername"].toString()
            password = project.properties["myNexusPassword"].toString()

        }
    }
    maven {
        name = "papermc"
        url = uri("https://papermc.io/repo/repository/maven-public/")
    }
    maven {
        name = "bungeecord"
        url = uri("https://repo.md-5.net/content/groups/public/")
    }
}

tasks.withType<JavaCompile> {
    sourceCompatibility = "17"
    targetCompatibility = "17"
}

tasks.test {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }

    repositories {
        maven {
            name = "minepalm-snapshots"
            url = uri("https://repo.minepalm.com/repository/maven-snapshots")
            credentials {
                username = project.properties["myNexusUsername"].toString()
                password = project.properties["myNexusPassword"].toString()
            }
        }

    }

}