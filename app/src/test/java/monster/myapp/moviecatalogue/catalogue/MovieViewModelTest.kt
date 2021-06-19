package monster.myapp.moviecatalogue.catalogue

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import kotlinx.coroutines.flow.flow
import monster.myapp.moviecatalogue.core.data.source.local.entity.MovieEntity
import monster.myapp.moviecatalogue.core.utils.DataDummy
import monster.myapp.moviecatalogue.core.data.Resource
import monster.myapp.moviecatalogue.core.domain.repository.ICatalogueRepository
import monster.myapp.moviecatalogue.core.domain.usecase.CatalogueInteractor
import monster.myapp.moviecatalogue.core.domain.usecase.CatalogueUseCase
import org.junit.Assert
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by robby on 06/05/21.
 */
@RunWith(MockitoJUnitRunner::class)
class MovieViewModelTest {

    private lateinit var catalogueUseCase: CatalogueUseCase
    private val query = "SELECT * FROM movie_entities"

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var catalogueRepository: ICatalogueRepository

    @Before
    fun setUp() {
        catalogueUseCase = CatalogueInteractor(catalogueRepository)
    }

    @Test
    fun getMovies() {
        val dummyMovies = DataDummy.generateDummyMovies()
        val data = Resource.Success(PagingData.from(dummyMovies))
        val movies = flow {
            emit(data)
        }

        `when`(catalogueRepository.getAllMovies(false)).thenReturn(movies)
        verify(catalogueRepository).getAllMovies(false)
        verifyNoMoreInteractions(catalogueRepository)
        Assert.assertEquals(data.data, dummyMovies)
    }

    /*@Test
    fun getFavoredMovies() {
        val dummyMovies = DataDummy.generateDummyMovies()
        val data = PagingData.from(dummyMovies)
        val movies = MutableLiveData<PagingData<MovieEntity>>()
        movies.value = data

        `when`(catalogueRepository.getFavoredMovies()).thenReturn(movies)
        val movieEntities = viewModel.getFavoredMovies().value
        verify(catalogueRepository).getFavoredMovies()
        assertNotNull(movieEntities)

        viewModel.getFavoredMovies().observeForever(favoredObserver)
        verify(favoredObserver).onChanged(data)
    }*/

}