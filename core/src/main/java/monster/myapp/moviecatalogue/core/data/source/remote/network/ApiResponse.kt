package monster.myapp.moviecatalogue.core.data.source.remote.network

/**
 * Created by robby on 12/05/21.
 */
sealed class ApiResponse<out R> {
    data class Success<out T>(val data: T) : ApiResponse<T>()
    data class Error(val errorMessage: String) : ApiResponse<Nothing>()
    object Empty : ApiResponse<Nothing>()
}