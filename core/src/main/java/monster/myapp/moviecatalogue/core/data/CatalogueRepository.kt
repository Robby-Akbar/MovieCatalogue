package monster.myapp.moviecatalogue.core.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import androidx.sqlite.db.SimpleSQLiteQuery
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import monster.myapp.moviecatalogue.core.data.source.local.LocalDataSource
import monster.myapp.moviecatalogue.core.data.source.remote.RemoteDataSource
import monster.myapp.moviecatalogue.core.data.source.remote.network.ApiResponse
import monster.myapp.moviecatalogue.core.data.source.remote.response.ListMovieResponse
import monster.myapp.moviecatalogue.core.data.source.remote.response.ListTvShowResponse
import monster.myapp.moviecatalogue.core.data.source.remote.response.MovieResponse
import monster.myapp.moviecatalogue.core.data.source.remote.response.TvShowResponse
import monster.myapp.moviecatalogue.core.domain.model.Catalogue
import monster.myapp.moviecatalogue.core.domain.repository.ICatalogueRepository
import monster.myapp.moviecatalogue.core.utils.AppExecutors
import monster.myapp.moviecatalogue.core.utils.DataMapper

/**
 * Created by robby on 07/05/21.
 */
class CatalogueRepository(
    private val remoteDataSource: RemoteDataSource, private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : ICatalogueRepository {

    private val config = PagingConfig(
        enablePlaceholders = false,
        initialLoadSize = 5,
        pageSize = 5
    )

    override fun getAllMovies(isRefresh: Boolean): Flow<Resource<PagingData<Catalogue>>> {
        return object : NetworkBoundResource<PagingData<Catalogue>, ListMovieResponse>() {
            public override fun loadFromDB(): Flow<PagingData<Catalogue>> =
                Pager(
                    config,
                    pagingSourceFactory = { localDataSource.getAllMovies() }
                ).flow.map {
                    it.map { movieEntity -> DataMapper.mapMovieEntityToDomain(movieEntity) }
                }

            override fun shouldFetch(data: PagingData<Catalogue>?): Boolean = isRefresh

            override suspend fun createCall(): Flow<ApiResponse<ListMovieResponse>> =
                remoteDataSource.getAllMovies()

            override suspend fun saveCallResult(data: ListMovieResponse) {
                val movieList = DataMapper.mapResListMovieToEntities(data.results, false)
                localDataSource.insertMovies(movieList)
            }
        }.asFlow()
    }

    override fun getMovie(id: Int): Flow<Resource<Catalogue>> {
        return object : NetworkBoundResource<Catalogue, MovieResponse>() {
            override fun loadFromDB(): Flow<Catalogue> = localDataSource.getMovie(id).map {
                DataMapper.mapMovieEntityToDomain(it)
            }

            override fun shouldFetch(data: Catalogue?): Boolean =
                data == null

            override suspend fun createCall(): Flow<ApiResponse<MovieResponse>> =
                remoteDataSource.getMovie(id)

            override suspend fun saveCallResult(data: MovieResponse) {
                localDataSource.updateMovie(DataMapper.mapResMovieToEntity(data))
            }
        }.asFlow()
    }

    override fun getSearchMovies(query: String): Flow<Resource<PagingData<Catalogue>>> {
        return object : NetworkBoundResource<PagingData<Catalogue>, ListMovieResponse>() {
            public override fun loadFromDB(): Flow<PagingData<Catalogue>> =
                Pager(
                    config,
                    pagingSourceFactory = {
                        localDataSource.getSearchMovies(
                            SimpleSQLiteQuery("SELECT * FROM movie_entities WHERE title LIKE '%$query%'")
                        )
                    }
                ).flow.map {
                    it.map { movieEntity -> DataMapper.mapMovieEntityToDomain(movieEntity) }
                }

            override fun shouldFetch(data: PagingData<Catalogue>?): Boolean = true

            override suspend fun createCall(): Flow<ApiResponse<ListMovieResponse>> =
                remoteDataSource.getSearchMovie(query)

            override suspend fun saveCallResult(data: ListMovieResponse) {
                val movieList = DataMapper.mapResListMovieToEntities(data.results, true)
                localDataSource.insertMovies(movieList)
            }
        }.asFlow()
    }

    override fun getAllTvShows(isRefresh: Boolean): Flow<Resource<PagingData<Catalogue>>> {
        return object : NetworkBoundResource<PagingData<Catalogue>, ListTvShowResponse>() {
            public override fun loadFromDB(): Flow<PagingData<Catalogue>> =
                Pager(
                    config,
                    pagingSourceFactory = { localDataSource.getAllTvShows() }
                ).flow.map {
                    it.map { tvShowEntity -> DataMapper.mapTvShowEntityToDomain(tvShowEntity) }
                }

            override fun shouldFetch(data: PagingData<Catalogue>?): Boolean = isRefresh

            override suspend fun createCall(): Flow<ApiResponse<ListTvShowResponse>> =
                remoteDataSource.getAllTvShows()

            override suspend fun saveCallResult(data: ListTvShowResponse) {
                val tvShowList = DataMapper.mapResListTvShowToEntities(data.results, false)
                localDataSource.insertTvShows(tvShowList)
            }
        }.asFlow()
    }

    override fun getTvShow(id: Int): Flow<Resource<Catalogue>> {
        return object : NetworkBoundResource<Catalogue, TvShowResponse>() {
            override fun loadFromDB(): Flow<Catalogue> = localDataSource.getTvShow(id).map {
                DataMapper.mapTvShowEntityToDomain(it)
            }

            override fun shouldFetch(data: Catalogue?): Boolean =
                data == null

            override suspend fun createCall(): Flow<ApiResponse<TvShowResponse>> =
                remoteDataSource.getTvShow(id)

            override suspend fun saveCallResult(data: TvShowResponse) {
                localDataSource.updateTvShow(DataMapper.mapResTvShowToEntity(data))
            }
        }.asFlow()
    }

    override fun getSearchTvShows(query: String): Flow<Resource<PagingData<Catalogue>>> {
        return object : NetworkBoundResource<PagingData<Catalogue>, ListTvShowResponse>() {
            public override fun loadFromDB(): Flow<PagingData<Catalogue>> =
                Pager(
                    config,
                    pagingSourceFactory = {
                        localDataSource.getSearchTvShows(
                            SimpleSQLiteQuery("SELECT * FROM tv_entities WHERE name LIKE '%$query%'")
                        )
                    }
                ).flow.map {
                    it.map { tvShowEntity -> DataMapper.mapTvShowEntityToDomain(tvShowEntity) }
                }

            override fun shouldFetch(data: PagingData<Catalogue>?): Boolean = true

            override suspend fun createCall(): Flow<ApiResponse<ListTvShowResponse>> =
                remoteDataSource.getSearchTvShow(query)

            override suspend fun saveCallResult(data: ListTvShowResponse) {
                val tvShowList = DataMapper.mapResListTvShowToEntities(data.results, true)
                localDataSource.insertTvShows(tvShowList)
            }
        }.asFlow()
    }

    override fun getFavoredMovies(): Flow<PagingData<Catalogue>> =
        Pager(
            config,
            pagingSourceFactory = { localDataSource.getFavoredMovies() }).flow.map {
            it.map { movieEntity -> DataMapper.mapMovieEntityToDomain(movieEntity) }
        }

    override fun getFavoredTvShows(): Flow<PagingData<Catalogue>> =
        Pager(
            config,
            pagingSourceFactory = { localDataSource.getFavoredTvShows() }).flow.map {
            it.map { tvShowEntity -> DataMapper.mapTvShowEntityToDomain(tvShowEntity) }
        }

    override fun setFavoredMovie(catalogue: Catalogue, state: Boolean) {
        val movieEntity = DataMapper.mapMovieDomainToEntity(catalogue)
        appExecutors.diskIO().execute {
            localDataSource.setFavoredMovie(movieEntity, state)
        }
    }

    override fun setFavoredTvShow(catalogue: Catalogue, state: Boolean) {
        val tvShowEntity = DataMapper.mapTvShowDomainToEntity(catalogue)
        appExecutors.diskIO().execute {
            localDataSource.setFavoredTvShow(tvShowEntity, state)
        }
    }
}