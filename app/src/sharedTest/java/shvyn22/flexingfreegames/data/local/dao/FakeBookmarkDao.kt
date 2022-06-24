package shvyn22.flexingfreegames.data.local.dao

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import shvyn22.flexingfreegames.data.local.model.GameModel

class FakeBookmarkDao : BookmarkDao {

    private val items = MutableStateFlow<MutableList<GameModel>>(mutableListOf())

    override fun getBookmarks(): Flow<List<GameModel>> = items

    override suspend fun isGameBookmarked(id: Int): Boolean {
        return items.value.any { it.id == id }
    }

    override suspend fun insertBookmark(item: GameModel) {
        items.value.add(item)
    }

    override suspend fun deleteBookmark(id: Int) {
        items.value.removeIf { it.id == id }
    }
}