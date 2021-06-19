package monster.myapp.moviecatalogue.core.data.source.remote.response

/**
 * Created by robby on 07/05/21.
 */
data class ListMovieResponse(
    val page: Int,
    val results: List<MovieResponse>,
    val total_pages: Int,
    val total_results: Int,
    val status_message: String
)
