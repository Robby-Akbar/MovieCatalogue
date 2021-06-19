package monster.myapp.moviecatalogue.core.domain.usecase

import monster.myapp.moviecatalogue.core.domain.model.Catalogue
import monster.myapp.moviecatalogue.core.domain.repository.ICatalogueRepository

/**
 * Created by robby on 15/06/21.
 */
class CatalogueInteractor(
    private val catalogueRepository: ICatalogueRepository
) : CatalogueUseCase {

    override fun getAllMovies(isRefresh: Boolean) =
        catalogueRepository.getAllMovies(isRefresh)

    override fun getMovie(id: Int) = catalogueRepository.getMovie(id)

    override fun getSearchMovies(query: String) =
        catalogueRepository.getSearchMovies(query)

    override fun getAllTvShows(isRefresh: Boolean) =
        catalogueRepository.getAllTvShows(isRefresh)

    override fun getTvShow(id: Int) = catalogueRepository.getTvShow(id)

    override fun getSearchTvShows(query: String) =
        catalogueRepository.getSearchTvShows(query)

    override fun getFavoredMovies() = catalogueRepository.getFavoredMovies()

    override fun getFavoredTvShows() = catalogueRepository.getFavoredTvShows()

    override fun setFavoredMovie(catalogue: Catalogue, state: Boolean) =
        catalogueRepository.setFavoredMovie(catalogue, state)

    override fun setFavoredTvShow(catalogue: Catalogue, state: Boolean) =
        catalogueRepository.setFavoredTvShow(catalogue, state)

}