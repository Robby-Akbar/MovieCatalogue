package monster.myapp.moviecatalogue.core.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import monster.myapp.moviecatalogue.core.data.Resource
import monster.myapp.moviecatalogue.core.domain.model.Catalogue

/**
 * Created by robby on 15/06/21.
 */
interface ICatalogueRepository {

    fun getAllMovies(isRefresh: Boolean): Flow<Resource<PagingData<Catalogue>>>
    fun getMovie(id: Int): Flow<Resource<Catalogue>>
    fun getSearchMovies(query: String): Flow<Resource<PagingData<Catalogue>>>

    fun getAllTvShows(isRefresh: Boolean): Flow<Resource<PagingData<Catalogue>>>
    fun getTvShow(id: Int): Flow<Resource<Catalogue>>
    fun getSearchTvShows(query: String): Flow<Resource<PagingData<Catalogue>>>

    fun getFavoredMovies(): Flow<PagingData<Catalogue>>
    fun getFavoredTvShows(): Flow<PagingData<Catalogue>>

    fun setFavoredMovie(catalogue: Catalogue, state: Boolean)
    fun setFavoredTvShow(catalogue: Catalogue, state: Boolean)

}