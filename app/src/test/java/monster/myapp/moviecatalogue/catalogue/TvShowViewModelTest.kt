package monster.myapp.moviecatalogue.catalogue

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import monster.myapp.moviecatalogue.core.domain.repository.ICatalogueRepository
import monster.myapp.moviecatalogue.core.domain.usecase.CatalogueInteractor
import monster.myapp.moviecatalogue.core.domain.usecase.CatalogueUseCase
import org.junit.Test
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by robby on 06/05/21.
 */
@RunWith(MockitoJUnitRunner::class)
class TvShowViewModelTest {

    private lateinit var catalogueUseCase: CatalogueUseCase

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var catalogueRepository: ICatalogueRepository

    @Before
    fun setUp() {
        catalogueUseCase = CatalogueInteractor(catalogueRepository)
    }

    @Test
    fun getTvSHows() {
        /*val dummyTvShows = DataDummy.generateDummyTvShows()
        val data = Resource.success(PagingData.from(dummyTvShows))
        val tvShows = MutableLiveData<Resource<PagingData<TvShowEntity>>>()
        tvShows.value = data

        `when`(catalogueRepository.getAllTvShows(false, query)).thenReturn(tvShows)

        viewModel.tvShows.observeForever(observer)
        verify(observer).onChanged(data)*/
    }

    /*@Test
    fun getFavoredTvShows() {
        val dummyTvShows = DataDummy.generateDummyTvShows()
        val data = PagingData.from(dummyTvShows)
        val tvShows = MutableLiveData<PagingData<TvShowEntity>>()
        tvShows.value = data

        `when`(catalogueRepository.getFavoredTvShows()).thenReturn(tvShows)
        val tvShowEntities = viewModel.getFavoredTvShows().value
        verify(catalogueRepository).getFavoredTvShows()
        assertNotNull(tvShowEntities)

        viewModel.getFavoredTvShows().observeForever(favoredObserver)
        verify(favoredObserver).onChanged(data)
    }*/

}