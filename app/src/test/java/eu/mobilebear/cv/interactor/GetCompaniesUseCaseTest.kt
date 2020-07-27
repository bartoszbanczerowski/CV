package eu.mobilebear.cv.interactor

import com.nhaarman.mockitokotlin2.whenever
import eu.mobilebear.cv.domain.interactor.GetCompaniesUseCase
import eu.mobilebear.cv.domain.model.CompanyValidationAction
import eu.mobilebear.cv.domain.model.CompanyValidationModel
import eu.mobilebear.cv.domain.repository.CVRepository
import eu.mobilebear.cv.networking.response.Company
import eu.mobilebear.cv.rx.RxFactory
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit

class GetCompaniesUseCaseTest {

    @JvmField
    @Rule
    var rule = MockitoJUnit.rule()

    @Mock
    lateinit var mockCVRepository: CVRepository

    @Mock
    lateinit var mockRxFactory: RxFactory

    @Mock
    lateinit var mockCompanyValidationModel: CompanyValidationModel

    @Mock
    lateinit var mockCompany: Company

    lateinit var getCompaniesUseCase: GetCompaniesUseCase

    @Before
    fun setUp() {
        getCompaniesUseCase = GetCompaniesUseCase(mockCVRepository, mockRxFactory)
        whenever(mockCVRepository.requestCompanies()).thenReturn(Single.just(mockCompanyValidationModel))
    }

    @Test
    fun `Should return NO_COMPANIES action when cv repository return no companies`() {
        // given
        whenever(mockCompanyValidationModel.status).thenReturn(CompanyValidationModel.NO_COMPANIES)

        // when
        val testObserver = getCompaniesUseCase.buildUseCaseSingle().test()

        // then
        testObserver
            .assertNoErrors()
            .assertComplete()
            .assertValue(CompanyValidationAction.NoCompanies)
    }

    @Test
    fun `Should return GENERAL ERROR action when cv repository return general error`() {
        // given
        whenever(mockCompanyValidationModel.status).thenReturn(CompanyValidationModel.GENERAL_ERROR)

        // when
        val testObserver = getCompaniesUseCase.buildUseCaseSingle().test()

        // then
        testObserver
            .assertNoErrors()
            .assertComplete()
            .assertValue(CompanyValidationAction.GeneralError)
    }

    @Test
    fun `Should return COMPANIES_DOWNLOADED action when cv repository return companies`() {
        // given
        whenever(mockCompanyValidationModel.status).thenReturn(CompanyValidationModel.COMPANIES_DOWNLOADED)
        whenever(mockCompanyValidationModel.companies).thenReturn(listOf(mockCompany))

        // when
        val testObserver = getCompaniesUseCase.buildUseCaseSingle().test()

        // then
        testObserver
            .assertNoErrors()
            .assertComplete()
            .assertOf { CompanyValidationAction.CompaniesDownloaded::class.java }
    }
}
