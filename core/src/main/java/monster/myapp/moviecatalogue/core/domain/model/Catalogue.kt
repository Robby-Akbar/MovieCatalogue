package monster.myapp.moviecatalogue.core.domain.model

/**
 * Created by robby on 15/06/21.
 */
data class Catalogue(
    var id: Int,
    var vote_average: Double,
    var title: String,
    var overview: String,
    var release_date: String,
    var poster_path: String,
    var backdrop_path: String,
    var favored: Boolean = false
)
