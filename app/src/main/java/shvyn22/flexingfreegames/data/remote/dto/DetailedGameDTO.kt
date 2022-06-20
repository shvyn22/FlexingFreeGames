package shvyn22.flexingfreegames.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DetailedGameDTO(
    @Json(name = "id")
    val id: Int,

    @Json(name = "title")
    val title: String,

    @Json(name = "release_date")
    val releaseDate: String,

    @Json(name = "publisher")
    val publisher: String,

    @Json(name = "developer")
    val developer: String,

    @Json(name = "genre")
    val genre: String,

    @Json(name = "platform")
    val platform: String,

    @Json(name = "short_description")
    val description: String,

    @Json(name = "description")
    val detailedDescription: String,

    @Json(name = "game_url")
    val gameUrl: String,

    @Json(name = "freetogame_profile_url")
    val freeToGameUrl: String,

    @Json(name = "thumbnail")
    val thumbnail: String,

    @Json(name = "minimum_system_requirements")
    val systemRequirements: SystemRequirementsDTO,

    @Json(name = "screenshots")
    val screenshots: List<ScreenshotDTO>
)

@JsonClass(generateAdapter = true)
data class SystemRequirementsDTO(

    @Json(name = "os")
    val os: String,

    @Json(name = "processor")
    val processor: String,

    @Json(name = "memory")
    val memory: String,

    @Json(name = "graphics")
    val graphics: String,

    @Json(name = "storage")
    val storage: String,
)

@JsonClass(generateAdapter = true)
data class ScreenshotDTO(

    @Json(name = "id")
    val id: Int,

    @Json(name = "image")
    val image: String,
)