package monster.myapp.moviecatalogue.core.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.sqlite.db.SimpleSQLiteQuery
import com.nhaarman.mockitokotlin2.doNothing
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import monster.myapp.moviecatalogue.core.data.source.local.LocalDataSource
import monster.myapp.moviecatalogue.core.data.source.local.entity.MovieEntity
import monster.myapp.moviecatalogue.core.data.source.local.entity.TvShowEntity
import monster.myapp.moviecatalogue.core.data.source.remote.RemoteDataSource
import monster.myapp.moviecatalogue.core.utils.AppExecutors
import monster.myapp.moviecatalogue.core.utils.DataDummy
import monster.myapp.moviecatalogue.core.utils.LiveDataTestUtil
import monster.myapp.moviecatalogue.core.utils.TestExecutor
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

/**
 * Created by robby on 08/05/21.
 */
class CatalogueRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val queryMovie = "SELECT * FROM movie_entities"
    private val queryTvShow = "SELECT * FROM tv_entities"

    private lateinit var sqlMovie: SimpleSQLiteQuery
    private lateinit var sqlTvShow: SimpleSQLiteQuery

    private val remote = mock(RemoteDataSource::class.java)
    private val local = mock(LocalDataSource::class.java)
    private val appExecutors: AppExecutors = AppExecutors(TestExecutor(), TestExecutor())

    private lateinit var catalogueRepository: FakeCatalogueRepository

    private val dummyMovies = DataDummy.generateDummyMovies()
    private val movieId = dummyMovies[0].id
    private val dummyTvShows = DataDummy.generateDummyTvShows()
    private val tvShowId = dummyTvShows[0].id

    @Before
    fun setUp() {
        catalogueRepository = FakeCatalogueRepository(remote, local, appExecutors)
        sqlMovie = SimpleSQLiteQuery(queryMovie)
        sqlTvShow = SimpleSQLiteQuery(queryTvShow)
    }

    @Test
    @Suppress("UNCHECKED_CAST")
    fun getAllMovies() {
        val pagingSourceFactory =
            mock(PagingSource::class.java) as PagingSource<Int, MovieEntity>
        `when`(local.getAllMovies(sqlMovie)).thenReturn(pagingSourceFactory)
        catalogueRepository.getAllMovies(false, queryMovie)
        local.getAllMovies(sqlMovie)

        val movieEntities = Resource.success(PagingData.from(dummyMovies))
        verify(local).getAllMovies(sqlMovie)
        assertNotNull(movieEntities.data)
    }

    @Test
    fun getMovie() {
        val movie = MutableLiveData<MovieEntity>()
        movie.value = dummyMovies[0]
        `when`(local.getMovie(movieId)).thenReturn(movie)

        val movieEntity = LiveDataTestUtil.getValue(catalogueRepository.getMovie(movieId))
        verify(local).getMovie(movieId)
        assertNotNull(movieEntity)
        assertEquals(dummyMovies[0].title, movieEntity.data?.title)
    }

    @Test
    @Suppress("UNCHECKED_CAST")
    fun getAllTvShows() {
        val pagingSourceFactory =
            mock(PagingSource::class.java) as PagingSource<Int, TvShowEntity>
        `when`(local.getAllTvShows(sqlTvShow)).thenReturn(pagingSourceFactory)
        catalogueRepository.getAllTvShows(false, queryTvShow)
        local.getAllTvShows(sqlTvShow)

        val tvShowEntities = Resource.success(PagingData.from(dummyTvShows))
        verify(local).getAllTvShows(sqlTvShow)
        assertNotNull(tvShowEntities.data)
    }

    @Test
    fun getTvShow() {
        val tvShow = MutableLiveData<TvShowEntity>()
        tvShow.value = dummyTvShows[0]
        `when`(local.getTvShow(tvShowId)).thenReturn(tvShow)

        val tvShowEntity = LiveDataTestUtil.getValue(catalogueRepository.getTvShow(tvShowId))
        verify(local).getTvShow(tvShowId)
        assertNotNull(tvShowEntity)
        assertEquals(dummyTvShows[0].name, tvShowEntity.data?.name)
    }

    @Test
    @Suppress("UNCHECKED_CAST")
    fun getFavoredMovies() {
        val pagingSourceFactory =
            mock(PagingSource::class.java) as PagingSource<Int, MovieEntity>
        `when`(local.getFavoredMovies()).thenReturn(pagingSourceFactory)

        val movies = MutableLiveData<PagingData<MovieEntity>>()
        movies.value = PagingData.from(dummyMovies)
        local.getFavoredMovies()

        val movieEntities = LiveDataTestUtil.getValue(movies)
        verify(local).getFavoredMovies()
        assertNotNull(movieEntities)
    }

    @Test
    @Suppress("UNCHECKED_CAST")
    fun getFavoredTvShows() {
        val pagingSourceFactory =
            mock(PagingSource::class.java) as PagingSource<Int, TvShowEntity>
        `when`(local.getFavoredTvShows()).thenReturn(pagingSourceFactory)

        val tvShows = MutableLiveData<PagingData<TvShowEntity>>()
        tvShows.value = PagingData.from(dummyTvShows)
        local.getFavoredTvShows()

        val tvShowEntities = LiveDataTestUtil.getValue(tvShows)
        verify(local).getFavoredTvShows()
        assertNotNull(tvShowEntities)
    }

    @Test
    fun setFavoredMovie() {
        val dataFavorite = dummyMovies[1].copy(favored = true)
        doNothing().`when`(local).setFavoredMovie(dataFavorite, true)
        catalogueRepository.setFavoredMovie(dataFavorite, true)
        verify(local, times(1)).setFavoredMovie(dataFavorite, true)
    }

    @Test
    fun setFavoredTvShow() {
        val dataFavorite = dummyTvShows[1].copy(favored = true)
        doNothing().`when`(local).setFavoredTvShow(dataFavorite, true)
        catalogueRepository.setFavoredTvShow(dataFavorite, true)
        verify(local, times(1)).setFavoredTvShow(dataFavorite, true)
    }

}