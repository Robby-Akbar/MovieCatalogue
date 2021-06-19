package monster.myapp.moviecatalogue.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import monster.myapp.moviecatalogue.core.data.source.local.entity.MovieEntity
import monster.myapp.moviecatalogue.core.data.source.local.entity.TvShowEntity

/**
 * Created by robby on 12/05/21.
 */
@Database(
    entities = [MovieEntity::class, TvShowEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CatalogueDatabase : RoomDatabase() {
    abstract fun catalogueDao(): CatalogueDao
}