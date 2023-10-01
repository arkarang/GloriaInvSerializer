import org.jetbrains.kotlin.gradle.plugin.mpp.pm20.util.archivesName

apply(plugin = "io.papermc.paperweight.userdev")

group = "kr.mcgloria"
version = "1.0-SNAPSHOT"

dependencies {
    implementation(project(mapOf("path" to ":common")))
    paperDevBundle("1.18.2-R0.1-SNAPSHOT")
    implementation("com.github.luben:zstd-jni:1.5.2-5")
}

tasks.jar {
    dependsOn("reobfJar")
    doLast {
        val jarFile = archiveFile.get().asFile
        //if (jarFile.exists() && jarFile.name.endsWith("-dev.jar")) {
        //    val newName = jarFile.name.replace("-dev.jar", ".jar")
        //    jarFile.renameTo(File(jarFile.parent, newName))
        //}
    }
}



tasks.shadowJar {
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