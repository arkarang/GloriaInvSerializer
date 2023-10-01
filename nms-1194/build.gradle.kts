import io.papermc.paperweight.util.constants.paperTaskOutput

apply(plugin = "io.papermc.paperweight.userdev")
apply(plugin = "com.github.johnrengelman.shadow")

group = "kr.mcgloria"
version = "1.0-SNAPSHOT"


dependencies {
    implementation(project(mapOf("path" to ":common")))
    paperDevBundle("1.19.4-R0.1-SNAPSHOT")
    implementation("com.github.luben:zstd-jni:1.5.2-5")
}

tasks.jar {
    dependsOn("reobfJar")
    archiveClassifier.set("")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])

            artifact(tasks.jar) {
                extension = "jar"
                classifier = ""
            }
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