package com.example.koinandktortutorial.domain.di

import com.example.koinandktortutorial.data.remote.UserApi
import com.example.koinandktortutorial.data.repository.GetListRepositoryImpl
import com.example.koinandktortutorial.domain.repository.GetListRepository
import com.example.koinandktortutorial.domain.usecase.GetListUseCase
import com.example.koinandktortutorial.ui.MainActivityViewModel
import com.example.koinandktortutorial.ui.MovieListViewModel
import com.example.koinandktortutorial.utils.HttpRoutes
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val appModules = module {
    // Singleton across application
    single {
        // http client (KTOR)
        Retrofit.Builder()
            .baseUrl(HttpRoutes.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(UserApi::class.java)
    }
    single<GetListRepository> {
        // repository file impl
        GetListRepositoryImpl(get())
    }
    single {
        // repository file impl
        GetListUseCase(get())
    }
//    single<MovieListService> {
//        // repository file impl
//        MovieListServiceImpl(get())
//    }

//    // new instance Loaded every time requested
//    factory {
//
//    }
    viewModel {
        MainActivityViewModel()
    }
    viewModel {
        MovieListViewModel()
    }

}