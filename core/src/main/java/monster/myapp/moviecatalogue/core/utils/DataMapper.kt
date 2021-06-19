package monster.myapp.moviecatalogue.core.utils

import monster.myapp.moviecatalogue.core.data.source.local.entity.MovieEntity
import monster.myapp.moviecatalogue.core.data.source.local.entity.TvShowEntity
import monster.myapp.moviecatalogue.core.data.source.remote.response.MovieResponse
import monster.myapp.moviecatalogue.core.data.source.remote.response.TvShowResponse
import monster.myapp.moviecatalogue.core.domain.model.Catalogue

/**
 * Created by robby on 15/06/21.
 */
object DataMapper {

    fun mapResMovieToEntity(input: MovieResponse) = MovieEntity(
        input.id, input.vote_average, input.title, input.overview, input.release_date, input.poster_path, input.backdrop_path, false
    )

    fun mapMovieDomainToEntity(input: Catalogue) = MovieEntity(
        input.id, input.vote_average, input.title, input.overview, input.release_date, input.poster_path, input.backdrop_path, input.favored
    )

    fun mapMovieEntityToDomain(input: MovieEntity) = Catalogue(
        input.id, input.vote_average, input.title.toString(), input.overview.toString(),
        input.release_date.toString(), input.poster_path.toString(),
        input.backdrop_path.toString(), input.favored
    )

    fun mapResListMovieToEntities(input: List<MovieResponse>, searched: Boolean): List<MovieEntity> {
        val movieList = ArrayList<MovieEntity>()
        input.map {
            val movie = MovieEntity(
                it.id, it.vote_average, it.title, it.overview, it.release_date, it.poster_path, it.backdrop_path, false, searched
            )
            movieList.add(movie)
        }
        return movieList
    }

    fun mapResTvShowToEntity(input: TvShowResponse) = TvShowEntity(
        input.id, input.vote_average, input.name, input.overview, input.first_air_date, input.poster_path, input.backdrop_path, false
    )

    fun mapTvShowDomainToEntity(input: Catalogue) = TvShowEntity(
        input.id, input.vote_average, input.title, input.overview, input.release_date, input.poster_path, input.backdrop_path, input.favored
    )

    fun mapTvShowEntityToDomain(input: TvShowEntity) = Catalogue(
        input.id, input.vote_average, input.name.toString(), input.overview.toString(),
        input.first_air_date.toString(), input.poster_path.toString(),
        input.backdrop_path.toString(), input.favored
    )

    fun mapResListTvShowToEntities(input: List<TvShowResponse>, searched: Boolean): List<TvShowEntity> {
        val tvShowList = ArrayList<TvShowEntity>()
        input.map {
            val tvShow = TvShowEntity(
                it.id, it.vote_average, it.name, it.overview, it.first_air_date, it.poster_path, it.backdrop_path, false, searched
            )
            tvShowList.add(tvShow)
        }
        return tvShowList
    }

}