import com.github.triplet.gradle.androidpublisher.ReleaseStatus
import com.github.triplet.gradle.play.tasks.PublishBundleTask

play {
    serviceAccountCredentials.set(file("publishing-service-account.json"))
    defaultToAppBundles.set(true)
    resolutionStrategy.set(com.github.triplet.gradle.play.ResolutionStrategy.IGNORE)
}

tasks.register("publishToProduction") {
    group = "publishing"
    description = "Publishes app to production track"
    dependsOn(":app:bundleRelease")

    doLast {
        publishAppBundle(
            track = "production",
            releaseStatus = ReleaseStatus.COMPLETED,
            releaseNotes = readReleaseNotes()
        )
    }
}

tasks.register("publishToStagedRollout") {
    group = "publishing"
    description = "Staged rollout to 20% users"
    dependsOn(":app:bundleRelease")

    doLast {
        publishAppBundle(
            track = "production",
            releaseStatus = ReleaseStatus.IN_PROGRESS,
            userFraction = 0.2,
            releaseNotes = readReleaseNotes()
        )
    }
}

fun readReleaseNotes(): Map<String, String> {
    val notesDir = file("release-notes")
    return mapOf(
        "en-US" to file("$notesDir/en-US.txt").takeIf { it.exists() }?.readText() ?: "",
        "ru-RU" to file("$notesDir/ru-RU.txt").takeIf { it.exists() }?.readText() ?: ""
    )
}

fun publishAppBundle(
    track: String,
    releaseStatus: ReleaseStatus,
    userFraction: Double? = null,
    releaseNotes: Map<String, String>
) {
    PublishBundleTask.create(
        project = project,
        extension = project.extensions.getByType(),
        variantName = "release",
        track = track,
        releaseStatus = releaseStatus,
        userFraction = userFraction,
        releaseNotes = releaseNotes
    ).also {
        it.commit.set(true)
        it.artifactType.set("AAB")
    }.execute()
}