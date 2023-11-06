plugins {
    id("java")
    id("maven-publish")
}

group = "kr.mcgloria"
version = "1.0-SNAPSHOT"


tasks.shadowJar {
    archiveClassifier.set("")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            artifact(tasks.jar) {
                extension = "jar"
                classifier = "z"
            }
        }
    }

}