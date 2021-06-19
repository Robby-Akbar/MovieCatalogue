package monster.myapp.moviecatalogue.core.data.source.remote.network

import monster.myapp.moviecatalogue.core.data.source.remote.response.ListMovieResponse
import monster.myapp.moviecatalogue.core.data.source.remote.response.ListTvShowResponse
import monster.myapp.moviecatalogue.core.data.source.remote.response.MovieResponse
import monster.myapp.moviecatalogue.core.data.source.remote.response.TvShowResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by robby on 07/05/21.
 */
interface ApiService {

    @GET("movie/popular")
    suspend fun getMovies(
        @Query("api_key") api_key: String,
        @Query("language") language: String
    ): ListMovieResponse

    @GET("movie/{id}")
    suspend fun getMovie(
        @Path("id") id: Int,
        @Query("api_key") api_key: String,
        @Query("language") language: String
    ): MovieResponse

    @GET("tv/popular")
    suspend fun getTvShows(
        @Query("api_key") api_key: String,
        @Query("language") language: String
    ): ListTvShowResponse

    @GET("tv/{id}")
    suspend fun getTvShow(
        @Path("id") id: Int,
        @Query("api_key") api_key: String,
        @Query("language") language: String
    ): TvShowResponse

    @GET("search/movie")
    suspend fun getSearchMovie(
        @Query("api_key") api_key: String,
        @Query("language") language: String,
        @Query("query") query: String
    ): ListMovieResponse

    @GET("search/tv")
    suspend fun getSearchTvShow(
        @Query("api_key") api_key: String,
        @Query("language") language: String,
        @Query("query") query: String
    ): ListTvShowResponse

}