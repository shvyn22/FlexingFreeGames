package shvyn22.flexingfreegames.data.remote.dto

import com.squareup.moshi.Json

data class GameDTO(

    @field:Json(name = "id")
    val id: Int,

    @field:Json(name = "title")
    val title: String,

    @field:Json(name = "release_date")
    val releaseDate: String,

    @field:Json(name = "publisher")
    val publisher: String,

    @field:Json(name = "short_description")
    val description: String,

    @field:Json(name = "thumbnail")
    val thumbnail: String,
)