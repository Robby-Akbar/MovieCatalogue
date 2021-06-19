package monster.myapp.moviecatalogue.core.data

import androidx.paging.*
import androidx.sqlite.db.SimpleSQLiteQuery
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import monster.myapp.moviecatalogue.core.data.source.local.LocalDataSource
import monster.myapp.moviecatalogue.core.data.source.remote.network.ApiResponse
import monster.myapp.moviecatalogue.core.data.source.remote.RemoteDataSource
import monster.myapp.moviecatalogue.core.data.source.remote.response.ListMovieResponse
import monster.myapp.moviecatalogue.core.data.source.remote.response.ListTvShowResponse
import monster.myapp.moviecatalogue.core.domain.model.Catalogue
import monster.myapp.moviecatalogue.core.domain.repository.ICatalogueRepository
import monster.myapp.moviecatalogue.core.utils.AppExecutors
import monster.myapp.moviecatalogue.core.utils.DataMapper

/**
 * Created by robby on 07/05/21.
 */
class FakeCatalogueRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : ICatalogueRepository {

    private val config = PagingConfig(
        enablePlaceholders = false,
        initialLoadSize = 5,
        pageSize = 5
    )

    override fun getAllMovies(
        isRefresh: Boolean, query: String
    ): Flow<Resource<PagingData<Catalogue>>> {
        return object : monster.myapp.moviecatalogue.core.data.NetworkBoundResource<PagingData<Catalogue>, ListMovieResponse>() {
            public override fun loadFromDB(): Flow<PagingData<Catalogue>> =
                Pager(
                    config,
                    pagingSourceFactory = { localDataSource.getAllMovies(SimpleSQLiteQuery(query)) }
                ).flow.map {
                    it.map { movieEntity -> DataMapper.mapMovieEntityToDomain(movieEntity) }
                }

            override fun shouldFetch(data: PagingData<Catalogue>?): Boolean = isRefresh

            override suspend fun createCall(): Flow<ApiResponse<ListMovieResponse>> =
                remoteDataSource.getAllMovies()

            override suspend fun saveCallResult(data: ListMovieResponse) {
                val movieList = DataMapper.mapResListMovieToEntities(data.results)
                localDataSource.insertMovies(movieList)
            }
        }.asFlow()
    }

    override fun getMovie(id: Int): Flow<Resource<Catalogue>> {
        return object : monster.myapp.moviecatalogue.core.data.NetworkBoundResource<Catalogue, Catalogue>() {
            override fun loadFromDB(): Flow<Catalogue> = localDataSource.getMovie(id).map {
                DataMapper.mapMovieEntityToDomain(it)
            }

            override fun shouldFetch(data: Catalogue?): Boolean =
                data == null

            override suspend fun createCall(): Flow<ApiResponse<Catalogue>> =
                remoteDataSource.getMovie(id)

            override suspend fun saveCallResult(data: Catalogue) {
                localDataSource.updateMovie(DataMapper.mapResMovieToEntity(data))
            }
        }.asFlow()
    }

    override fun getAllTvShows(
        isRefresh: Boolean, query: String
    ): Flow<Resource<PagingData<TvShow>>> {
        return object :
            monster.myapp.moviecatalogue.core.data.NetworkBoundResource<PagingData<TvShow>, ListTvShowResponse>() {
            public override fun loadFromDB(): Flow<PagingData<TvShow>> =
                Pager(
                    config,
                    pagingSourceFactory = { localDataSource.getAllTvShows(SimpleSQLiteQuery(query)) }
                ).flow.map {
                    it.map { tvShowEntity -> DataMapper.mapTvShowEntityToDomain(tvShowEntity) }
                }

            override fun shouldFetch(data: PagingData<TvShow>?): Boolean = isRefresh

            override suspend fun createCall(): Flow<ApiResponse<ListTvShowResponse>> =
                remoteDataSource.getAllTvShows()

            override suspend fun saveCallResult(data: ListTvShowResponse) {
                val tvShowList = DataMapper.mapResListTvShowToEntities(data.results)
                localDataSource.insertTvShows(tvShowList)
            }
        }.asFlow()
    }

    override fun getTvShow(id: Int): Flow<Resource<TvShow>> {
        return object : monster.myapp.moviecatalogue.core.data.NetworkBoundResource<TvShow, TvShow>() {
            override fun loadFromDB(): Flow<TvShow> = localDataSource.getTvShow(id).map {
                DataMapper.mapTvShowEntityToDomain(it)
            }

            override fun shouldFetch(data: TvShow?): Boolean =
                data == null

            override suspend fun createCall(): Flow<ApiResponse<TvShow>> =
                remoteDataSource.getTvShow(id)

            override suspend fun saveCallResult(data: TvShow) {
                localDataSource.updateTvShow(DataMapper.mapResTvShowToEntity(data))
            }
        }.asFlow()
    }

    override fun getFavoredMovies(): Flow<PagingData<Catalogue>> =
        Pager(config, pagingSourceFactory = { localDataSource.getFavoredMovies() }).flow.map {
            it.map { movieEntity -> DataMapper.mapMovieEntityToDomain(movieEntity) }
        }

    override fun getFavoredTvShows(): Flow<PagingData<TvShow>> =
        Pager(config, pagingSourceFactory = { localDataSource.getFavoredTvShows() }).flow.map {
            it.map { tvShowEntity -> DataMapper.mapTvShowEntityToDomain(tvShowEntity) }
        }

    override fun setFavoredMovie(catalogue: Catalogue, state: Boolean) {
        val movieEntity = DataMapper.mapResMovieToEntity(catalogue)
        appExecutors.diskIO().execute {
            localDataSource.setFavoredMovie(movieEntity, state)
        }
    }

    override fun setFavoredTvShow(tvShow: TvShow, state: Boolean) {
        val tvShowEntity = DataMapper.mapResTvShowToEntity(tvShow)
        appExecutors.diskIO().execute {
            localDataSource.setFavoredTvShow(tvShowEntity, state)
        }
    }

}