package shvyn22.flexingfreegames.data.local.model

data class DetailedGameModel(
    val id: Int,
    val title: String,
    val releaseDate: String,
    val publisher: String,
    val developer: String,
    val genre: String,
    val platform: String,
    val description: String,
    val detailedDescription: String,
    val gameUrl: String,
    val freeToGameUrl: String,
    val thumbnail: String,
    val systemRequirements: SystemRequirementsModel?,
    val screenshots: List<ScreenshotModel>
)

data class SystemRequirementsModel(
    val os: String,
    val processor: String,
    val memory: String,
    val graphics: String,
    val storage: String,
)

data class ScreenshotModel(
    val id: Int,
    val image: String,
)
