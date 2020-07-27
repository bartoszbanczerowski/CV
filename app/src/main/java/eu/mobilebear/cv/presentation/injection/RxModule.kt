package eu.mobilebear.cv.presentation.injection

import eu.mobilebear.cv.data.repository.CVRepositoryImpl
import eu.mobilebear.cv.networking.CVService
import eu.mobilebear.cv.rx.RxFactory
import eu.mobilebear.cv.util.ConnectionChecker
import org.koin.dsl.module

val rxModule = module {
    single { providesRxFactory() }
    single { provideCVRepositoryImpl(get(), get()) }
}

private fun providesRxFactory() = RxFactory()

private fun provideCVRepositoryImpl(cvService: CVService, connectionChecker: ConnectionChecker) =
    CVRepositoryImpl(cvService, connectionChecker)