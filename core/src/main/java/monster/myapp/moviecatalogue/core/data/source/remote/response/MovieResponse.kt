package monster.myapp.moviecatalogue.core.data.source.remote.response

/**
 * Created by robby on 17/06/21.
 */
data class MovieResponse(
    var id: Int,
    var vote_average: Double,
    var title: String,
    var overview: String,
    var release_date: String,
    var poster_path: String,
    var backdrop_path: String
)
