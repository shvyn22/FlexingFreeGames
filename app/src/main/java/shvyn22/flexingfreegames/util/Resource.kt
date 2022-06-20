package shvyn22.flexingfreegames.util

sealed class Resource<T> {
    data class Success<T>(val data: T) : Resource<T>()
    class Loading<T> : Resource<T>()
    class Idle<T> : Resource<T>()
    data class Error<T>(val error: ResourceError) : Resource<T>()
}

sealed class ResourceError {
    object Fetching : ResourceError()
    object NoBookmarks : ResourceError()
    data class Specified(val msg: String) : ResourceError()
}