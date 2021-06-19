package monster.myapp.moviecatalogue.catalogue

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import monster.myapp.moviecatalogue.core.domain.model.Catalogue
import monster.myapp.moviecatalogue.core.domain.usecase.CatalogueUseCase
import monster.myapp.moviecatalogue.core.utils.DoubleTrigger

/**
 * Created by robby on 06/05/21.
 */
class CatalogueViewModel(private val catalogueUseCase: CatalogueUseCase) : ViewModel() {

    val isRefresh = MutableLiveData<Boolean>().apply { postValue(false) }
    val query = MutableLiveData<String>()

    var movies = Transformations.switchMap(DoubleTrigger(isRefresh, query)) { value ->
        if (value.second.isNullOrEmpty()) value.first?.let {
            catalogueUseCase.getAllMovies(it).asLiveData()
        }
        else value.second?.let { catalogueUseCase.getSearchMovies(it).asLiveData() }
    }

    var tvShows = Transformations.switchMap(DoubleTrigger(isRefresh, query)) { value ->
        if (value.second.isNullOrEmpty()) value.first?.let {
            catalogueUseCase.getAllTvShows(it).asLiveData()
        }
        else value.second?.let { catalogueUseCase.getSearchTvShows(it).asLiveData() }
    }

    fun getFavoredMovies() = catalogueUseCase.getFavoredMovies().asLiveData()
    fun getFavoredTvShows() = catalogueUseCase.getFavoredTvShows().asLiveData()

    fun setFavoriteMovie(catalogue: Catalogue, newState: Boolean) {
        catalogueUseCase.setFavoredMovie(catalogue, newState)
    }

    fun setFavoriteTvShow(catalogue: Catalogue, newState: Boolean) {
        catalogueUseCase.setFavoredTvShow(catalogue, newState)
    }

}