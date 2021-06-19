package monster.myapp.moviecatalogue.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by robby on 05/05/21.
 */
@Entity(tableName = "tv_entities")
data class TvShowEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "tvId")
    var id: Int,

    @ColumnInfo(name = "vote_average")
    var vote_average: Double,

    @ColumnInfo(name = "name")
    var name: String?,

    @ColumnInfo(name = "overview")
    var overview: String?,

    @ColumnInfo(name = "first_air_date")
    var first_air_date: String?,

    @ColumnInfo(name = "poster_path")
    var poster_path: String?,

    @ColumnInfo(name = "backdrop_path")
    var backdrop_path: String?,

    @ColumnInfo(name = "favored")
    var favored: Boolean = false,

    @ColumnInfo(name = "searched")
    var searched: Boolean = false
)
