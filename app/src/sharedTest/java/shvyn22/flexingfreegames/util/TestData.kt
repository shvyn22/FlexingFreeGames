package shvyn22.flexingfreegames.util

import shvyn22.flexingfreegames.data.remote.dto.DetailedGameDTO
import shvyn22.flexingfreegames.data.remote.dto.GameDTO
import shvyn22.flexingfreegames.data.remote.dto.ScreenshotDTO
import shvyn22.flexingfreegames.data.remote.dto.SystemRequirementsDTO

val game1 = GameDTO(
    id = 516,
    title = "PUBG: BATTLEGROUNDS",
    releaseDate = "2022-01-12",
    publisher = "KRAFTON, Inc.",
    description = "Get into the action in one of the longest running battle royale games PUBG Battlegrounds.",
    thumbnail = "https://www.freetogame.com/g/516/thumbnail.jpg"
)

val game2 = GameDTO(
    id = 521,
    title = "Diablo Immortal",
    releaseDate = "2022-06-02",
    publisher = "Blizzard Entertainment",
    description = "Built for mobile and also released on PC, Diablo Immortal fills in the gaps between Diablo II and III in an MMOARPG environment.",
    thumbnail = "https://www.freetogame.com/g/521/thumbnail.jpg"
)

val game3 = DetailedGameDTO(
    id = 66,
    title = "Gwent: The Witcher Card Game",
    releaseDate = "2017-05-24",
    publisher = "CD Projekt Red",
    developer = "CD Projekt Red",
    genre = "Card Game",
    platform = "Windows",
    description = "A free-to-play card game based on CD Projekt Red's popular Witcher franchise. ",
    detailedDescription = "Gwent is a free-to-play card game for PC and XBox One, based on CD Projekt Red’s popular Witcher franchise. The game features both a multi-player and single-player mode with adventures, skirmishes and a player vs. AI mode. All of this is developed by the same team behind The Witcher III: Wild Hunt.\r\n\r\nIn fact, the core game is based on the card game featured in Wild Hunt, but with several new additions. The stand alone card game features new card art, voice overs and skills. There is even a competitive PvP mode built completely from scratch for Gwent.\r\n\r\nAs with any card game, players will be able to choose a deck type (or faction), build it out, and go into battle against other players. Gwent features a gameboard with a battlefield built of three rows, melee, ranged, and siege. Each row can be populated with cards of varying stats. A game is won when a player takes two out of three rounds.",
    gameUrl = "https://www.freetogame.com/open/gwent",
    freeToGameUrl = "https://www.freetogame.com/gwent",
    thumbnail = "https://www.freetogame.com/g/66/thumbnail.jpg",
    systemRequirements = SystemRequirementsDTO(
        os = " Windows 7/8/8.1/10 (64 bit)",
        processor = "Intel Celeron G1820 | AMD A4-7300",
        memory = "2 GB RAM",
        graphics = "NVIDIA GeForce GT 710 or GeForce GT 7900 | AMD Radeon R5 330 or Radeon HD 4650",
        storage = "4.5 GB available space"
    ),
    screenshots = listOf(
        ScreenshotDTO(
            id = 219,
            image ="https://www.freetogame.com/g/66/gwent-1.jpg"
        ),
        ScreenshotDTO(
            id = 220,
            image = "https://www.freetogame.com/g/66/gwent-2.jpg"
        )
    )
)

const val TEST_DATASTORE_FILENAME = "preferences_test"