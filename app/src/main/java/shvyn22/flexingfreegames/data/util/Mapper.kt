package shvyn22.flexingfreegames.data.util

import shvyn22.flexingfreegames.data.local.model.DetailedGameModel
import shvyn22.flexingfreegames.data.local.model.GameModel
import shvyn22.flexingfreegames.data.local.model.ScreenshotModel
import shvyn22.flexingfreegames.data.local.model.SystemRequirementsModel
import shvyn22.flexingfreegames.data.remote.dto.DetailedGameDTO
import shvyn22.flexingfreegames.data.remote.dto.GameDTO
import shvyn22.flexingfreegames.data.remote.dto.ScreenshotDTO
import shvyn22.flexingfreegames.data.remote.dto.SystemRequirementsDTO

fun fromGameDTOToModel(items: List<GameDTO>) =
    items.map { fromGameDTOToModel(it) }

fun fromGameDTOToModel(item: GameDTO) =
    GameModel(
        id = item.id,
        title = item.title,
        releaseDate = item.releaseDate,
        publisher = item.publisher,
        description = item.description,
        thumbnail = item.thumbnail
    )

fun fromDetailedGameDTOToModel(item: DetailedGameDTO) =
    DetailedGameModel(
        id = item.id,
        title = item.title,
        releaseDate = item.releaseDate,
        publisher = item.publisher,
        developer = item.developer,
        genre = item.genre,
        platform = item.platform,
        description = item.description,
        detailedDescription = item.detailedDescription,
        gameUrl = item.gameUrl,
        freeToGameUrl = item.freeToGameUrl,
        thumbnail = item.thumbnail,
        systemRequirements = fromSystemRequirementsDTOToModel(item.systemRequirements),
        screenshots = fromScreenshotDTOToModel(item.screenshots)
    )

fun fromSystemRequirementsDTOToModel(item: SystemRequirementsDTO) =
    SystemRequirementsModel(
        os = item.os,
        processor = item.processor,
        memory = item.memory,
        graphics = item.graphics,
        storage = item.storage
    )

fun fromScreenshotDTOToModel(items: List<ScreenshotDTO>) =
    items.map { fromScreenshotDTOToModel(it) }

fun fromScreenshotDTOToModel(item: ScreenshotDTO) =
    ScreenshotModel(
        id = item.id,
        image = item.image
    )

fun fromDetailedGameToGame(item: DetailedGameModel) =
    GameModel(
        id = item.id,
        title = item.title,
        releaseDate = item.releaseDate,
        publisher = item.publisher,
        description = item.description,
        thumbnail = item.thumbnail
    )