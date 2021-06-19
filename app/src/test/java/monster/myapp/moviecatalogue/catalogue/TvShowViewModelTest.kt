package monster.myapp.moviecatalogue.catalogue

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagingData
import monster.myapp.moviecatalogue.core.data.CatalogueRepository
import monster.myapp.moviecatalogue.core.data.source.local.entity.TvShowEntity
import monster.myapp.moviecatalogue.core.utils.DataDummy
import monster.myapp.moviecatalogue.core.data.Resource
import org.junit.Assert.*
import org.junit.Test
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by robby on 06/05/21.
 */
@RunWith(MockitoJUnitRunner::class)
class TvShowViewModelTest {

    //private lateinit var viewModel: TvShowViewModel
    private val query = "SELECT * FROM tv_entities"

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var catalogueRepository: CatalogueRepository

    @Mock
    private lateinit var observer: Observer<Resource<PagingData<TvShowEntity>>>

    @Mock
    private lateinit var favoredObserver: Observer<PagingData<TvShowEntity>>

    /*@Before
    fun setUp() {
        viewModel = TvShowViewModel(catalogueRepository)
    }

    @Test
    fun getTvSHows() {
        val dummyTvShows = DataDummy.generateDummyTvShows()
        val data = Resource.success(PagingData.from(dummyTvShows))
        val tvShows = MutableLiveData<Resource<PagingData<TvShowEntity>>>()
        tvShows.value = data

        `when`(catalogueRepository.getAllTvShows(false, query)).thenReturn(tvShows)

        viewModel.tvShows.observeForever(observer)
        verify(observer).onChanged(data)
    }

    @Test
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