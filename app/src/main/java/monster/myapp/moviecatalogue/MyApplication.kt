package monster.myapp.moviecatalogue

import android.app.Application
import monster.myapp.moviecatalogue.core.di.databaseModule
import monster.myapp.moviecatalogue.core.di.networkModule
import monster.myapp.moviecatalogue.core.di.repositoryModule
import monster.myapp.moviecatalogue.di.useCaseModule
import monster.myapp.moviecatalogue.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

/**
 * Created by robby on 16/06/21.
 */
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }

}