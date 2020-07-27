package eu.mobilebear.cv.repository

import com.nhaarman.mockitokotlin2.whenever

import eu.mobilebear.cv.data.repository.CVRepositoryImpl
import eu.mobilebear.cv.domain.model.CompanyValidationModel
import eu.mobilebear.cv.domain.model.HobbyValidationModel
import eu.mobilebear.cv.networking.CVService
import eu.mobilebear.cv.networking.response.Company
import eu.mobilebear.cv.networking.response.Hobby
import eu.mobilebear.cv.util.ConnectionChecker
import eu.mobilebear.cv.util.NetworkException
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import retrofit2.Response

class CommentRepositoryImplTest {


    @JvmField
    @Rule
    var rule = MockitoJUnit.rule()

    @Mock
    lateinit var mockCVService: CVService

    @Mock
    lateinit var mockConnectionChecker: ConnectionChecker

    @Mock
    lateinit var mockCompaniesResponse: Response<List<Company>>

    @Mock
    lateinit var mockHobbiesResponse: Response<List<Hobby>>

    @Mock
    lateinit var mockCompany: Company

    @Mock
    lateinit var mockHobby: Hobby

    @Mock
    lateinit var mockThrowable: Throwable

    lateinit var cvRepositoryImpl: CVRepositoryImpl

    @Before
    fun setUp(){
        cvRepositoryImpl = CVRepositoryImpl(mockCVService, mockConnectionChecker)
    }

    @Test
    fun `Should getCompanies() throw network exception when there is no connection`() {
        // given
        whenever(mockConnectionChecker.isConnected).thenReturn(false)

        // when
        val testObserver = cvRepositoryImpl.requestCompanies().test()

        // then
        testObserver.assertError(NetworkException::class.java)
    }

    @Test
    fun `Should getHobbies() throw network exception when there is no connection`() {
        // given
        whenever(mockConnectionChecker.isConnected).thenReturn(false)

        // when
        val testObserver = cvRepositoryImpl.requestHobbies().test()

        // then
        testObserver.assertError(NetworkException::class.java)
    }

    @Test
    fun `Should getCompanies() return CompanyValidationModel COMPANIES_DOWNLOADED state`() {
        // given
        whenever(mockConnectionChecker.isConnected).thenReturn(true)
        whenever(mockCVService.getCompanies()).thenReturn(Single.just(mockCompaniesResponse))
        whenever(mockCompaniesResponse.isSuccessful).thenReturn(true)
        whenever(mockCompaniesResponse.body()).thenReturn(listOf(mockCompany))

        // when
        val testObserver = cvRepositoryImpl.requestCompanies().test()

        // then
        testObserver
            .assertComplete()
            .assertNoErrors()
            .assertValue(CompanyValidationModel(listOf(mockCompany), CompanyValidationModel.COMPANIES_DOWNLOADED))
    }

    @Test
    fun `Should getHobbies() return HobbyValidationModel HOBBIES_DOWNLOADED state`() {
        // given
        whenever(mockConnectionChecker.isConnected).thenReturn(true)
        whenever(mockCVService.getHobbies()).thenReturn(Single.just(mockHobbiesResponse))
        whenever(mockHobbiesResponse.isSuccessful).thenReturn(true)
        whenever(mockHobbiesResponse.body()).thenReturn(listOf(mockHobby))

        // when
        val testObserver = cvRepositoryImpl.requestHobbies().test()

        // then
        testObserver
            .assertComplete()
            .assertNoErrors()
            .assertValue(HobbyValidationModel(listOf(mockHobby), HobbyValidationModel.HOBBIES_DOWNLOADED))
    }

    @Test
    fun `Should getCompanies() return CompanyValidationModel GENERAL ERROR state when response is not successful`() {
        // given
        whenever(mockConnectionChecker.isConnected).thenReturn(true)
        whenever(mockCVService.getCompanies()).thenReturn(Single.just(mockCompaniesResponse))
        whenever(mockCompaniesResponse.isSuccessful).thenReturn(false)
        whenever(mockCompaniesResponse.body()).thenReturn(emptyList())

        // when
        val testObserver = cvRepositoryImpl.requestCompanies().test()

        // then
        testObserver
            .assertComplete()
            .assertNoErrors()
            .assertValue(CompanyValidationModel(emptyList(), CompanyValidationModel.GENERAL_ERROR))
    }

    @Test
    fun `Should getCompanies() return CompanyValidationModel GENERAL ERROR state when error happens`() {
        // given
        whenever(mockConnectionChecker.isConnected).thenReturn(true)
        whenever(mockCVService.getCompanies()).thenReturn(Single.error(mockThrowable))
        whenever(mockCompaniesResponse.isSuccessful).thenReturn(false)
        whenever(mockCompaniesResponse.body()).thenReturn(emptyList())

        // when
        val testObserver = cvRepositoryImpl.requestCompanies().test()

        // then
        testObserver
            .assertComplete()
            .assertNoErrors()
            .assertValue(CompanyValidationModel(emptyList(), CompanyValidationModel.GENERAL_ERROR))
    }

    @Test
    fun `Should getHobbies() return HobbyValidationModel GENERAL ERROR state when response is not successful`() {
        // given
        whenever(mockConnectionChecker.isConnected).thenReturn(true)
        whenever(mockCVService.getHobbies()).thenReturn(Single.just(mockHobbiesResponse))
        whenever(mockHobbiesResponse.isSuccessful).thenReturn(false)
        whenever(mockHobbiesResponse.body()).thenReturn(emptyList())

        // when
        val testObserver = cvRepositoryImpl.requestHobbies().test()

        // then
        testObserver
            .assertComplete()
            .assertNoErrors()
            .assertValue(HobbyValidationModel(emptyList(), HobbyValidationModel.GENERAL_ERROR))
    }

    @Test
    fun `Should getHobbies() return HobbyValidationModel GENERAL ERROR state when error happens`() {
        // given
        whenever(mockConnectionChecker.isConnected).thenReturn(true)
        whenever(mockCVService.getHobbies()).thenReturn(Single.error(mockThrowable))
        whenever(mockHobbiesResponse.isSuccessful).thenReturn(false)
        whenever(mockHobbiesResponse.body()).thenReturn(emptyList())

        // when
        val testObserver = cvRepositoryImpl.requestHobbies().test()

        // then
        testObserver
            .assertComplete()
            .assertNoErrors()
            .assertValue(HobbyValidationModel(emptyList(), HobbyValidationModel.GENERAL_ERROR))
    }
}
