package eu.mobilebear.cv

import android.app.Application
import eu.mobilebear.cv.presentation.injection.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class CVApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initTimber()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@CVApplication)
            modules(
                listOf(
                    applicationModule, networkModule, rxModule,
                    dashBoardViewModel, homeViewModel, linkedinViewModel
                )
            )

            if (BuildConfig.DEBUG) {
                androidLogger()
            }
        }
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}