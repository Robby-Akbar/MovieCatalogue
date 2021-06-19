package monster.myapp.moviecatalogue.core.di

import androidx.room.Room
import monster.myapp.moviecatalogue.core.BuildConfig
import monster.myapp.moviecatalogue.core.data.CatalogueRepository
import monster.myapp.moviecatalogue.core.data.source.local.LocalDataSource
import monster.myapp.moviecatalogue.core.data.source.local.room.CatalogueDatabase
import monster.myapp.moviecatalogue.core.data.source.remote.RemoteDataSource
import monster.myapp.moviecatalogue.core.data.source.remote.network.ApiService
import monster.myapp.moviecatalogue.core.domain.repository.ICatalogueRepository
import monster.myapp.moviecatalogue.core.utils.AppExecutors
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by robby on 16/06/21.
 */
val databaseModule = module {
    factory { get<CatalogueDatabase>().catalogueDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            CatalogueDatabase::class.java, "Catalogues.db"
        ).fallbackToDestructiveMigration().build()
    }
}

val networkModule = module {
    single {
        val httpClient = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            httpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        }
        httpClient.connectTimeout(20, TimeUnit.MINUTES) // connect timeout
            .writeTimeout(30, TimeUnit.MINUTES) // write timeout
            .readTimeout(30, TimeUnit.MINUTES)
            .callTimeout(2, TimeUnit.MINUTES) // read timeout
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    single { AppExecutors() }
    single<ICatalogueRepository> {
        CatalogueRepository(
            get(),
            get(),
            get()
        )
    }
}