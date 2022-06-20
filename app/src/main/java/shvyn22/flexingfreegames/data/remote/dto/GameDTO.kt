package shvyn22.flexingfreegames.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GameDTO(

    @Json(name = "id")
    val id: Int,

    @Json(name = "title")
    val title: String,

    @Json(name = "release_date")
    val releaseDate: String,

    @Json(name = "publisher")
    val publisher: String,

    @Json(name = "short_description")
    val description: String,

    @Json(name = "thumbnail")
    val thumbnail: String,
)