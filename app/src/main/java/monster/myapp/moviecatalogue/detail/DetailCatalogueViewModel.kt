package monster.myapp.moviecatalogue.detail

import androidx.lifecycle.*
import monster.myapp.moviecatalogue.core.domain.usecase.CatalogueUseCase

/**
 * Created by robby on 06/05/21.
 */
class DetailCatalogueViewModel(private val catalogueUseCase: CatalogueUseCase) : ViewModel() {

    val id = MutableLiveData<Int>()

    fun setSelectedCatalogue(idCatalogue: Int) {
        this.id.value = idCatalogue
    }

    var movie = Transformations.switchMap(id) {
        catalogueUseCase.getMovie(it).asLiveData()
    }

    var tvShow = Transformations.switchMap(id) {
        catalogueUseCase.getTvShow(it).asLiveData()
    }

    fun setFavoredMovie() {
        val movieResource = movie.value
        if (movieResource != null) {
            val movieEntity = movieResource.data
            if (movieEntity != null) {
                val newState = !movieEntity.favored
                catalogueUseCase.setFavoredMovie(movieEntity, newState)
            }
        }
    }

    fun setFavoredTvShow() {
        val tvShowResource = tvShow.value
        if (tvShowResource != null) {
            val tvShowEntity = tvShowResource.data
            if (tvShowEntity != null) {
                val newState = !tvShowEntity.favored
                catalogueUseCase.setFavoredTvShow(tvShowEntity, newState)
            }
        }
    }

}