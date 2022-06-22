package shvyn22.flexingfreegames.data.remote.dto

import com.squareup.moshi.Json

data class DetailedGameDTO(
    @field:Json(name = "id")
    val id: Int,

    @field:Json(name = "title")
    val title: String,

    @field:Json(name = "release_date")
    val releaseDate: String,

    @field:Json(name = "publisher")
    val publisher: String,

    @field:Json(name = "developer")
    val developer: String,

    @field:Json(name = "genre")
    val genre: String,

    @field:Json(name = "platform")
    val platform: String,

    @field:Json(name = "short_description")
    val description: String,

    @field:Json(name = "description")
    val detailedDescription: String,

    @field:Json(name = "game_url")
    val gameUrl: String,

    @field:Json(name = "freetogame_profile_url")
    val freeToGameUrl: String,

    @field:Json(name = "thumbnail")
    val thumbnail: String,

    @field:Json(name = "minimum_system_requirements")
    val systemRequirements: SystemRequirementsDTO?,

    @field:Json(name = "screenshots")
    val screenshots: List<ScreenshotDTO>?
)

data class SystemRequirementsDTO(

    @field:Json(name = "os")
    val os: String,

    @field:Json(name = "processor")
    val processor: String,

    @field:Json(name = "memory")
    val memory: String,

    @field:Json(name = "graphics")
    val graphics: String,

    @field:Json(name = "storage")
    val storage: String,
)

data class ScreenshotDTO(

    @field:Json(name = "id")
    val id: Int,

    @field:Json(name = "image")
    val image: String,
)