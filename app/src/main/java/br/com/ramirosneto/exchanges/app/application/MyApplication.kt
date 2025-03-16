package br.com.ramirosneto.exchanges.app.application

import android.app.Application
import br.com.ramirosneto.exchanges.app.di.AppModule.networkModule
import br.com.ramirosneto.exchanges.app.di.AppModule.repositoryModule
import br.com.ramirosneto.exchanges.app.di.AppModule.useCaseModule
import br.com.ramirosneto.exchanges.app.di.AppModule.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(networkModule, repositoryModule, useCaseModule, viewModelModule)
        }
    }
}
