package eu.mobilebear.cv.interactor

import com.nhaarman.mockitokotlin2.whenever
import eu.mobilebear.cv.domain.interactor.GetHobbiesUseCase
import eu.mobilebear.cv.domain.model.CompanyValidationAction
import eu.mobilebear.cv.domain.model.CompanyValidationModel
import eu.mobilebear.cv.domain.model.HobbyValidationAction
import eu.mobilebear.cv.domain.model.HobbyValidationModel
import eu.mobilebear.cv.domain.repository.CVRepository
import eu.mobilebear.cv.networking.response.Hobby
import eu.mobilebear.cv.rx.RxFactory
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit

class GetHobbiesUseCaseTest {

    @JvmField
    @Rule
    var rule = MockitoJUnit.rule()

    @Mock
    lateinit var mockCVRepository: CVRepository

    @Mock
    lateinit var mockRxFactory: RxFactory

    @Mock
    lateinit var mockHobbyValidationModel: HobbyValidationModel

    @Mock
    lateinit var mockHobby: Hobby

    lateinit var getHobbiesUseCase: GetHobbiesUseCase

    @Before
    fun setUp() {
        getHobbiesUseCase = GetHobbiesUseCase(mockCVRepository, mockRxFactory)
        whenever(mockCVRepository.requestHobbies()).thenReturn(Single.just(mockHobbyValidationModel))
    }

    @Test
    fun `Should return NO_HOBBIES action when cv repository return no hobbies`() {
        // given
        whenever(mockHobbyValidationModel.status).thenReturn(HobbyValidationModel.NO_HOBBIES)

        // when
        val testObserver = getHobbiesUseCase.buildUseCaseSingle().test()

        // then
        testObserver
            .assertNoErrors()
            .assertComplete()
            .assertValue(HobbyValidationAction.NoHobbies)
    }

    @Test
    fun `Should return GENERAL ERROR action when cv repository return general error`() {
        // given
        whenever(mockHobbyValidationModel.status).thenReturn(HobbyValidationModel.GENERAL_ERROR)

        // when
        val testObserver = getHobbiesUseCase.buildUseCaseSingle().test()

        // then
        testObserver
            .assertNoErrors()
            .assertComplete()
            .assertValue(HobbyValidationAction.GeneralError)
    }

    @Test
    fun `Should return HOBBIES_DOWNLOADED action when cv repository return hobbies`() {
        // given
        whenever(mockHobbyValidationModel.status).thenReturn(HobbyValidationModel.HOBBIES_DOWNLOADED)
        whenever(mockHobbyValidationModel.hobbies).thenReturn(listOf(mockHobby))

        // when
        val testObserver = getHobbiesUseCase.buildUseCaseSingle().test()

        // then
        testObserver
            .assertNoErrors()
            .assertComplete()
            .assertOf { HobbyValidationAction.HobbiesDownloaded::class.java }
    }
}
