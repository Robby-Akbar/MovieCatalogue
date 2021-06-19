package monster.myapp.moviecatalogue.core.data.source.local.room

import androidx.paging.PagingSource
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import kotlinx.coroutines.flow.Flow
import monster.myapp.moviecatalogue.core.data.source.local.entity.MovieEntity
import monster.myapp.moviecatalogue.core.data.source.local.entity.TvShowEntity

/**
 * Created by robby on 12/05/21.
 */
@Dao
interface CatalogueDao {

    @RawQuery(observedEntities = [MovieEntity::class])
    fun getMovies(query: SupportSQLiteQuery): PagingSource<Int, MovieEntity>

    @RawQuery(observedEntities = [TvShowEntity::class])
    fun getTvShows(query: SupportSQLiteQuery): PagingSource<Int, TvShowEntity>

    @Query("SELECT * FROM movie_entities where favored = 1")
    fun getFavoredMovies(): PagingSource<Int, MovieEntity>

    @Query("SELECT * FROM tv_entities where favored = 1")
    fun getFavoredTvShows(): PagingSource<Int, TvShowEntity>

    @Query("SELECT * FROM movie_entities WHERE movieId = :movieId")
    fun getMovieById(movieId: Int): Flow<MovieEntity>

    @Query("SELECT * FROM tv_entities WHERE tvId = :tvId")
    fun getTvShowById(tvId: Int): Flow<TvShowEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovies(movieEntities: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTvShows(tvShows: List<TvShowEntity>)

    @Update
    fun updateMovie(movieEntity: MovieEntity)

    @Update
    fun updateTvShow(tvShow: TvShowEntity)

}