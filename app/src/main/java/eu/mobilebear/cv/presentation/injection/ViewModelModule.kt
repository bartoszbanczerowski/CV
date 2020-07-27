package eu.mobilebear.cv.presentation.injection

import eu.mobilebear.cv.data.repository.CVRepositoryImpl
import eu.mobilebear.cv.domain.interactor.GetCompaniesUseCase
import eu.mobilebear.cv.domain.interactor.GetHobbiesUseCase
import eu.mobilebear.cv.presentation.ui.dashboard.DashboardViewModel
import eu.mobilebear.cv.presentation.ui.dashboard.adapter.CompaniesAdapter
import eu.mobilebear.cv.presentation.ui.hobby.HobbyViewModel
import eu.mobilebear.cv.presentation.ui.hobby.adapter.HobbyAdapter
import eu.mobilebear.cv.presentation.ui.notifications.NotificationsViewModel
import eu.mobilebear.cv.rx.RxFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeViewModel = module {
    factory { provideGetHobbiesUsacase(get(), get()) }
    factory { provideHobbyAdapter() }
    viewModel { HobbyViewModel(get()) }
}

val dashBoardViewModel = module {
    factory { provideGetCompaniesUsacase(get(), get()) }
    factory { provideCompaniesAdapter() }
    viewModel { DashboardViewModel(get()) }
}

val notificationViewModel = module {
    viewModel { NotificationsViewModel() }
}


private fun provideGetCompaniesUsacase(cvRepository: CVRepositoryImpl, rxFactory: RxFactory) = GetCompaniesUseCase(cvRepository, rxFactory)

private fun provideGetHobbiesUsacase(cvRepository: CVRepositoryImpl, rxFactory: RxFactory) = GetHobbiesUseCase(cvRepository, rxFactory)

private fun provideCompaniesAdapter() = CompaniesAdapter()

private fun provideHobbyAdapter() = HobbyAdapter()