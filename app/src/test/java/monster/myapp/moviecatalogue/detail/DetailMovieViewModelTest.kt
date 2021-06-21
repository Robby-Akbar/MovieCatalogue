package monster.myapp.moviecatalogue.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import monster.myapp.moviecatalogue.core.data.CatalogueRepository
import monster.myapp.moviecatalogue.core.data.source.local.entity.MovieEntity
import monster.myapp.moviecatalogue.core.data.source.local.entity.TvShowEntity
import monster.myapp.moviecatalogue.core.utils.DataDummy
import monster.myapp.moviecatalogue.core.data.Resource
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by robby on 06/05/21.
 */
@RunWith(MockitoJUnitRunner::class)
class DetailMovieViewModelTest {

    /*private lateinit var viewModel: DetailCatalogueViewModel

    private val dummyMovie = DataDummy.generateDummyMovies()[0]
    private val movieId = dummyMovie.id

    private val dummyTvShow = DataDummy.generateDummyTvShows()[0]
    private val tvShowId = dummyTvShow.id

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var catalogueRepository: CatalogueRepository

    @Mock
    private lateinit var observerMovieEntity: Observer<Resource<MovieEntity>>

    @Mock
    private lateinit var observerTv: Observer<Resource<TvShowEntity>>

    @Before
    fun setUp() {
        viewModel = DetailCatalogueViewModel(catalogueRepository)
    }

    @Test
    fun getMovie() {
        val movie = MutableLiveData<Resource<MovieEntity>>()
        val resource = Resource.success(dummyMovie)
        movie.value = resource
        viewModel.setSelectedCatalogue(movieId)

        `when`(catalogueRepository.getMovie(movieId)).thenReturn(movie)

        viewModel.movieEntity.observeForever(observerMovieEntity)
        verify(observerMovieEntity).onChanged(resource)
    }*/

    @Test
    fun getTvShow() {
        /*val tvShow = MutableLiveData<Resource<TvShowEntity>>()
        val resource = Resource.success(dummyTvShow)
        tvShow.value = resource
        viewModel.setSelectedCatalogue(tvShowId)

        `when`(catalogueRepository.getTvShow(tvShowId)).thenReturn(tvShow)

        viewModel.tvShow.observeForever(observerTv)
        verify(observerTv).onChanged(resource)*/
    }
}
