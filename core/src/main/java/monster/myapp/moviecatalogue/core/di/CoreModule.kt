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
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
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
        val passphrase: ByteArray = SQLiteDatabase.getBytes("myapp".toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(
            androidContext(),
            CatalogueDatabase::class.java, "Catalogues.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }
}

val networkModule = module {
    single {
        val hostname = "api.themoviedb.org"
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, "sha256/+vqZVAzTqUP8BGkfl88yU7SQ3C8J2uNEa55B7RZjEg0=")
            .add(hostname, "sha256/JSMzqOOrtyOT1kmau6zKhgT676hGgczD5VMdRMyJZFA=")
            .add(hostname, "sha256/++MBgDH5WGvL9Bcn5Be30cRcL0f5O+NyoXuWtQdX1aI=")
            .add(hostname, "sha256/KwccWaCgrnaw6tsrrSO61FgLacNgG2MMLq8GE6+oP5I=")
            .build()
        val httpClient = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            httpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        }
        httpClient.connectTimeout(20, TimeUnit.MINUTES) // connect timeout
            .writeTimeout(30, TimeUnit.MINUTES) // write timeout
            .readTimeout(30, TimeUnit.MINUTES)
            .callTimeout(2, TimeUnit.MINUTES) // read timeout
            .certificatePinner(certificatePinner)
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