package br.com.ramirosneto.exchanges.app.di

import br.com.ramirosneto.exchanges.app.data.remote.ExchangeRepositoryImpl
import br.com.ramirosneto.exchanges.app.data.remote.api.ApiService
import br.com.ramirosneto.exchanges.app.domain.repository.ExchangeRepository
import br.com.ramirosneto.exchanges.app.domain.usecase.GetExchangesUseCase
import br.com.ramirosneto.exchanges.app.presentation.viewmodel.ExchangeViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object AppModule {

    val networkModule = module {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        single {
            Retrofit.Builder()
                .baseUrl("https://rest.coinapi.io/")
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
                .create(ApiService::class.java)
        }
    }

    val repositoryModule = module {
        single<ExchangeRepository> { ExchangeRepositoryImpl(get()) }
    }

    val useCaseModule = module {
        factory { GetExchangesUseCase(get()) }
    }

    val viewModelModule = module {
        viewModel { ExchangeViewModel(get()) }
    }
}
