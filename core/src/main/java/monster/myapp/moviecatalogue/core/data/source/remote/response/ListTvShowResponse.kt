package monster.myapp.moviecatalogue.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

/**
 * Created by robby on 07/05/21.
 */
data class ListTvShowResponse(
    @field:SerializedName("results")
    val results: List<TvShowResponse>
)
