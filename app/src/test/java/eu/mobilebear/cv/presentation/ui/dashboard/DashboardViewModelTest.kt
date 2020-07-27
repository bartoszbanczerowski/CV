package eu.mobilebear.cv.presentation.ui.dashboard

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.*
import eu.mobilebear.cv.domain.interactor.GetCompaniesUseCase
import eu.mobilebear.cv.domain.model.CompanyValidationAction
import eu.mobilebear.cv.domain.model.CompanyValidationModel
import eu.mobilebear.cv.networking.response.Company
import eu.mobilebear.cv.networking.response.Hobby
import eu.mobilebear.cv.util.NetworkException
import eu.mobilebear.cv.util.state.NetworkStatus
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit

class DashboardViewModelTest {

    @JvmField
    @Rule
    var rule = MockitoJUnit.rule()

    @JvmField
    @Rule
    var viewModelRule: TestRule = InstantTaskExecutorRule()

    @Mock
    lateinit var mockGetCompaniesUseCase: GetCompaniesUseCase

    @Mock
    lateinit var mockNetworkException: NetworkException

    @Mock
    lateinit var mockCompanyValidationModel: CompanyValidationModel

    @Mock
    lateinit var mockCompany: Company

    lateinit var dashboardViewModel: DashboardViewModel

    @Before
    fun setUp() {
        dashboardViewModel = DashboardViewModel(mockGetCompaniesUseCase)
    }

    @Test
    fun `Should clean useCase when viewModel is cleared`() {

        // when
        dashboardViewModel.onCleared()

        // then
        verify(mockGetCompaniesUseCase).dispose()
    }

    @Test
    fun `Should execute call to get Companies when it's called`() {
        // when
        dashboardViewModel.getCompanies()

        // then
        verify(mockGetCompaniesUseCase).execute(observer = any(), params = isNull())
        assertEquals("Network Status is Running", NetworkStatus.Running, dashboardViewModel.mutableScreenState.value?.networkStatus)
        assertEquals("ViewModel has no companies", emptyList<Hobby>(), dashboardViewModel.mutableScreenState.value?.companies)
    }

    @Test
    fun `Should be in error state when get Companies return general error`() {
        //given
        dashboardViewModel.getCompanies()
        val captorPostsObserver = argumentCaptor<DashboardViewModel.CompaniesObserver>()
        verify(mockGetCompaniesUseCase).execute(captorPostsObserver.capture(), isNull())
        val hobbiesObserver = captorPostsObserver.firstValue

        // when
        hobbiesObserver.onSuccess(CompanyValidationAction.GeneralError)

        // then
        assertEquals("Network Status is Error", NetworkStatus.Error(null), dashboardViewModel.mutableScreenState.value?.networkStatus)
        assertEquals("ViewModel has no companies", emptyList<Hobby>(), dashboardViewModel.mutableScreenState.value?.companies)
    }

    @Test
    fun `Should be in error state when get Companies return network exception`() {
        //given
        dashboardViewModel.getCompanies()
        val captorPostsObserver = argumentCaptor<DashboardViewModel.CompaniesObserver>()
        verify(mockGetCompaniesUseCase).execute(captorPostsObserver.capture(), isNull())
        val hobbiesObserver = captorPostsObserver.firstValue

        // when
        hobbiesObserver.onError(mockNetworkException)

        // then
        assertEquals("Network Status is Network Error", NetworkStatus.NoConnectionError, dashboardViewModel.mutableScreenState.value?.networkStatus)
        assertEquals("ViewModel has no companies", emptyList<Hobby>(), dashboardViewModel.mutableScreenState.value?.companies)
    }

    @Test
    fun `Should be in no items state when get Companies return no companies`() {
        //given
        dashboardViewModel.getCompanies()
        val captorPostsObserver = argumentCaptor<DashboardViewModel.CompaniesObserver>()
        verify(mockGetCompaniesUseCase).execute(captorPostsObserver.capture(), isNull())
        val hobbiesObserver = captorPostsObserver.firstValue

        // when
        hobbiesObserver.onSuccess(CompanyValidationAction.NoCompanies)

        // then
        assertEquals("Network Status is NoItems", NetworkStatus.NoItems, dashboardViewModel.mutableScreenState.value?.networkStatus)
        assertEquals("ViewModel has no companies", emptyList<Hobby>(), dashboardViewModel.mutableScreenState.value?.companies)
    }

    @Test
    fun `Should be in success state when get Companies return companies`() {
        //given
        whenever(mockCompanyValidationModel.companies).thenReturn(listOf(mockCompany))
        dashboardViewModel.getCompanies()
        val captorPostsObserver = argumentCaptor<DashboardViewModel.CompaniesObserver>()
        verify(mockGetCompaniesUseCase).execute(captorPostsObserver.capture(), isNull())
        val hobbiesObserver = captorPostsObserver.firstValue

        // when
        hobbiesObserver.onSuccess(CompanyValidationAction.CompaniesDownloaded(mockCompanyValidationModel))

        // then
        assertEquals("Network Status is Success", NetworkStatus.Success, dashboardViewModel.mutableScreenState.value?.networkStatus)
        assertEquals("ViewModel has exactly same amount of companies", listOf(mockCompany), dashboardViewModel.mutableScreenState.value?.companies)
    }
}
