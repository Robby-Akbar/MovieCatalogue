package monster.myapp.moviecatalogue.core.data.source.remote.response

/**
 * Created by robby on 07/05/21.
 */
data class ListTvShowResponse(
    val page: Int,
    val results: List<TvShowResponse>,
    val total_pages: Int,
    val total_results: Int,
    val status_message: String
)
