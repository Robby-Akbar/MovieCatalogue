package monster.myapp.moviecatalogue.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

/**
 * Created by robby on 17/06/21.
 */
data class MovieResponse(
    @field:SerializedName("id")
    var id: Int,

    @field:SerializedName("vote_average")
    var vote_average: Double,

    @field:SerializedName("title")
    var title: String,

    @field:SerializedName("overview")
    var overview: String,

    @field:SerializedName("release_date")
    var release_date: String,

    @field:SerializedName("poster_path")
    var poster_path: String,

    @field:SerializedName("backdrop_path")
    var backdrop_path: String
)
