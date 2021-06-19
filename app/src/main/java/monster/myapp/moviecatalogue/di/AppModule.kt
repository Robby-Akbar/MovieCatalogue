package monster.myapp.moviecatalogue.di

import monster.myapp.moviecatalogue.catalogue.CatalogueViewModel
import monster.myapp.moviecatalogue.core.domain.usecase.CatalogueInteractor
import monster.myapp.moviecatalogue.core.domain.usecase.CatalogueUseCase
import monster.myapp.moviecatalogue.detail.DetailCatalogueViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by robby on 16/06/21.
 */
val useCaseModule = module {
    factory<CatalogueUseCase> { CatalogueInteractor(get()) }
}

val viewModelModule = module {
    viewModel { CatalogueViewModel(get()) }
    viewModel { DetailCatalogueViewModel(get()) }
}