package monster.myapp.moviecatalogue.core.data.source.remote.response

/**
 * Created by robby on 17/06/21.
 */
data class TvShowResponse(
    var id: Int,
    var vote_average: Double,
    var name: String,
    var overview: String,
    var first_air_date: String,
    var poster_path: String,
    var backdrop_path: String
)
