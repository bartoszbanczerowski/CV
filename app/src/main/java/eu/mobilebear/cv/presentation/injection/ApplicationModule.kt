package eu.mobilebear.cv.presentation.injection

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import eu.mobilebear.cv.util.AndroidObjectsFactory
import eu.mobilebear.cv.util.ConnectionChecker
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module


val applicationModule = module {
    single { providesAndroidObjectFactory() }
    single { providesConnectivityManager(androidApplication(), get()) }
    single { provideSettingsPreferences(androidApplication()) }
}

private const val SHARED_PREFERENCES = "SHARED_PREFERENCES"

private fun provideSettingsPreferences(application: Application): SharedPreferences =
    application.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)

private fun providesConnectivityManager(
    application: Application,
    androidObjectsFactory: AndroidObjectsFactory
): ConnectionChecker = ConnectionChecker(application, androidObjectsFactory)

private fun providesAndroidObjectFactory() = AndroidObjectsFactory()


