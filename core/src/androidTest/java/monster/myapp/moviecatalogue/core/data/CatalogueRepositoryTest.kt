package monster.myapp.moviecatalogue.core.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.nhaarman.mockitokotlin2.doNothing
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import monster.myapp.moviecatalogue.core.data.source.local.LocalDataSource
import monster.myapp.moviecatalogue.core.data.source.local.entity.MovieEntity
import monster.myapp.moviecatalogue.core.data.source.local.entity.TvShowEntity
import monster.myapp.moviecatalogue.core.data.source.remote.RemoteDataSource
import monster.myapp.moviecatalogue.core.domain.model.Catalogue
import monster.myapp.moviecatalogue.core.utils.*
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
    }

    @Test
    @Suppress("UNCHECKED_CAST")
    fun getAllMovies() {
        val pagingSourceFactory =
            mock(PagingSource::class.java) as PagingSource<Int, MovieEntity>
        `when`(local.getAllMovies()).thenReturn(pagingSourceFactory)
        catalogueRepository.getAllMovies(false)
        local.getAllMovies()

        val movieEntities = Resource.Success(PagingData.from(dummyMovies))
        verify(local).getAllMovies()
        assertNotNull(movieEntities.data)
    }

    @Test
    fun getMovie() {
        val movie = MutableLiveData<Catalogue>()
        movie.value = dummyMovies[0]
        /*`when`(local.getMovie(movieId)).thenReturn(movie)*/

        val movieEntity = LiveDataTestUtil.getValue(catalogueRepository.getMovie(movieId).asLiveData())
        verify(local).getMovie(movieId)
        assertNotNull(movieEntity)
        assertEquals(dummyMovies[0].title, movieEntity.data?.title)
    }

    @Test
    @Suppress("UNCHECKED_CAST")
    fun getAllTvShows() {
        val pagingSourceFactory =
            mock(PagingSource::class.java) as PagingSource<Int, TvShowEntity>
        `when`(local.getAllTvShows()).thenReturn(pagingSourceFactory)
        catalogueRepository.getAllTvShows(false)
        local.getAllTvShows()

        val tvShowEntities = Resource.Success(PagingData.from(dummyTvShows))
        verify(local).getAllTvShows()
        assertNotNull(tvShowEntities.data)
    }

    @Test
    fun getTvShow() {
        val tvShow = MutableLiveData<TvShowEntity>()
        tvShow.value = dummyTvShows[0]
        /*`when`(local.getTvShow(tvShowId)).thenReturn(tvShow)*/

        val tvShowEntity = LiveDataTestUtil.getValue(catalogueRepository.getTvShow(tvShowId).asLiveData())
        verify(local).getTvShow(tvShowId)
        assertNotNull(tvShowEntity)
        assertEquals(dummyTvShows[0].name, tvShowEntity.data?.title)
    }

    @Test
    @Suppress("UNCHECKED_CAST")
    fun getFavoredMovies() {
        val pagingSourceFactory =
            mock(PagingSource::class.java) as PagingSource<Int, MovieEntity>
        `when`(local.getFavoredMovies()).thenReturn(pagingSourceFactory)

        val movies = MutableLiveData<PagingData<Catalogue>>()
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
        doNothing().`when`(local).setFavoredMovie(DataMapper.mapMovieDomainToEntity(dataFavorite, false), true)
        catalogueRepository.setFavoredMovie(dataFavorite, true)
        verify(local, times(1)).setFavoredMovie(DataMapper.mapMovieDomainToEntity(dataFavorite, false), true)
    }

    @Test
    fun setFavoredTvShow() {
        val dataFavorite = dummyTvShows[1].copy(favored = true)
        doNothing().`when`(local).setFavoredTvShow(dataFavorite, true)
        catalogueRepository.setFavoredTvShow(DataMapper.mapTvShowEntityToDomain(dataFavorite), true)
        verify(local, times(1)).setFavoredTvShow(dataFavorite, true)
    }

}