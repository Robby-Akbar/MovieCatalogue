package monster.myapp.moviecatalogue.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

/**
 * Created by robby on 17/06/21.
 */
data class TvShowResponse(
    @field:SerializedName("id")
    var id: Int,

    @field:SerializedName("vote_average")
    var vote_average: Double,

    @field:SerializedName("name")
    var name: String,

    @field:SerializedName("overview")
    var overview: String,

    @field:SerializedName("first_air_date")
    var first_air_date: String,

    @field:SerializedName("poster_path")
    var poster_path: String,

    @field:SerializedName("backdrop_path")
    var backdrop_path: String
)
