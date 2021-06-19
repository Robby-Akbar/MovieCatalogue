package monster.myapp.moviecatalogue.core.data.source.remote

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import monster.myapp.moviecatalogue.core.BuildConfig
import monster.myapp.moviecatalogue.core.data.source.remote.network.ApiResponse
import monster.myapp.moviecatalogue.core.data.source.remote.network.ApiService
import monster.myapp.moviecatalogue.core.data.source.remote.response.ListMovieResponse
import monster.myapp.moviecatalogue.core.data.source.remote.response.ListTvShowResponse
import monster.myapp.moviecatalogue.core.data.source.remote.response.MovieResponse
import monster.myapp.moviecatalogue.core.data.source.remote.response.TvShowResponse

/**
 * Created by robby on 07/05/21.
 */
class RemoteDataSource(private val apiService: ApiService) {

    suspend fun getAllMovies(): Flow<ApiResponse<ListMovieResponse>> {
        return flow {
            try {
                val response = apiService.getMovies(API_KEY, language)
                val dataArray = response.results
                if (dataArray.isNotEmpty()){
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e(TAG, e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getMovie(id: Int): Flow<ApiResponse<MovieResponse>> {
        return flow {
            try {
                val response = apiService.getMovie(id, API_KEY, language)
                emit(ApiResponse.Success(response))
            } catch (e: Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e(TAG, e.toString())
            }
        }
    }

    suspend fun getAllTvShows(): Flow<ApiResponse<ListTvShowResponse>> {
        return flow {
            try {
                val response = apiService.getTvShows(API_KEY, language)
                val dataArray = response.results
                if (dataArray.isNotEmpty()){
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e(TAG, e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getTvShow(id: Int): Flow<ApiResponse<TvShowResponse>> {
        return flow {
            try {
                val response = apiService.getTvShow(id, API_KEY, language)
                emit(ApiResponse.Success(response))
            } catch (e: Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e(TAG, e.toString())
            }
        }
    }

    suspend fun getSearchMovie(query: String): Flow<ApiResponse<ListMovieResponse>> {
        return flow {
            try {
                val response = apiService.getSearchMovie(API_KEY, language, query)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e(TAG, e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getSearchTvShow(query: String): Flow<ApiResponse<ListTvShowResponse>> {
        return flow {
            try {
                val response = apiService.getSearchTvShow(API_KEY, language, query)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e(TAG, e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    companion object {
        private const val API_KEY: String = BuildConfig.API_KEY
        private const val language: String = "en-US"
        private val TAG: String = RemoteDataSource::class.java.simpleName
    }

}