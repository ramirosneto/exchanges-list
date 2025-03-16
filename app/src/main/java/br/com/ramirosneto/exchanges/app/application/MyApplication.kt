package br.com.ramirosneto.exchanges.app.application

import android.app.Application
import br.com.ramirosneto.exchanges.app.di.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(AppModule.networkModule, AppModule.repositoryModule, AppModule.viewModelModule)
        }
    }
}
