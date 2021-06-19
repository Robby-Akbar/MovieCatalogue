package monster.myapp.moviecatalogue.core.data.source.local

import androidx.paging.PagingSource
import androidx.sqlite.db.SimpleSQLiteQuery
import monster.myapp.moviecatalogue.core.data.source.local.entity.MovieEntity
import monster.myapp.moviecatalogue.core.data.source.local.entity.TvShowEntity
import monster.myapp.moviecatalogue.core.data.source.local.room.CatalogueDao

/**
 * Created by robby on 12/05/21.
 */
class LocalDataSource(private val mCatalogueDao: CatalogueDao) {

    fun getAllMovies(): PagingSource<Int, MovieEntity> = mCatalogueDao.getMovies(SimpleSQLiteQuery("SELECT * FROM movie_entities WHERE searched = 0"))
    fun getMovie(id: Int) = mCatalogueDao.getMovieById(id)
    fun getSearchMovies(query: SimpleSQLiteQuery): PagingSource<Int, MovieEntity> = mCatalogueDao.getMovies(query)

    fun getAllTvShows(): PagingSource<Int, TvShowEntity> = mCatalogueDao.getTvShows(SimpleSQLiteQuery("SELECT * FROM tv_entities WHERE searched = 0"))
    fun getTvShow(id: Int) = mCatalogueDao.getTvShowById(id)
    fun getSearchTvShows(query: SimpleSQLiteQuery): PagingSource<Int, TvShowEntity> = mCatalogueDao.getTvShows(query)

    fun getFavoredMovies(): PagingSource<Int, MovieEntity> = mCatalogueDao.getFavoredMovies()
    fun getFavoredTvShows(): PagingSource<Int, TvShowEntity> = mCatalogueDao.getFavoredTvShows()

    suspend fun insertMovies(movieEntities: List<MovieEntity>) = mCatalogueDao.insertMovies(movieEntities)
    suspend fun insertTvShows(tvShows: List<TvShowEntity>) = mCatalogueDao.insertTvShows(tvShows)

    fun updateMovie(movieEntity: MovieEntity) = mCatalogueDao.updateMovie(movieEntity)
    fun updateTvShow(tvShow: TvShowEntity) = mCatalogueDao.updateTvShow(tvShow)

    fun setFavoredMovie(movieEntity: MovieEntity, newState: Boolean) {
        movieEntity.favored = newState
        mCatalogueDao.updateMovie(movieEntity)
    }

    fun setFavoredTvShow(tvShow: TvShowEntity, newState: Boolean) {
        tvShow.favored = newState
        mCatalogueDao.updateTvShow(tvShow)
    }

}