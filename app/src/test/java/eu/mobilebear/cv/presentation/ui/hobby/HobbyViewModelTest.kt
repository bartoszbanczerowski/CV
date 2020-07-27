package eu.mobilebear.cv.presentation.ui.hobby

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.*
import eu.mobilebear.cv.domain.interactor.GetHobbiesUseCase
import eu.mobilebear.cv.domain.model.HobbyValidationAction
import eu.mobilebear.cv.domain.model.HobbyValidationModel
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

class HobbyViewModelTest {

    @JvmField
    @Rule
    var rule = MockitoJUnit.rule()

    @JvmField
    @Rule
    var viewModelRule: TestRule = InstantTaskExecutorRule()

    @Mock
    lateinit var mockGetHobbiesUseCase: GetHobbiesUseCase

    @Mock
    lateinit var mockNetworkException: NetworkException

    @Mock
    lateinit var mockHobbyValidationModel: HobbyValidationModel

    @Mock
    lateinit var mockHobby: Hobby

    lateinit var hobbyViewModel: HobbyViewModel

    @Before
    fun setUp() {
        hobbyViewModel = HobbyViewModel(mockGetHobbiesUseCase)
    }

    @Test
    fun `Should clean useCase when viewModel is cleared`() {

        // when
        hobbyViewModel.onCleared()

        // then
        verify(mockGetHobbiesUseCase).dispose()
    }

    @Test
    fun `Should execute call to get hobbies when it's called`() {
        // when
        hobbyViewModel.getHobbies()

        // then
        verify(mockGetHobbiesUseCase).execute(observer = any(), params = isNull())
        assertEquals("Network Status is Running", NetworkStatus.Running, hobbyViewModel.mutableScreenState.value?.networkStatus)
        assertEquals("ViewModel has no hobbies", emptyList<Hobby>(), hobbyViewModel.mutableScreenState.value?.hobbies)
    }

    @Test
    fun `Should be in error state when get Hobbies return general error`() {
        //given
        hobbyViewModel.getHobbies()
        val captorPostsObserver = argumentCaptor<HobbyViewModel.HobbiesObserver>()
        verify(mockGetHobbiesUseCase).execute(captorPostsObserver.capture(), isNull())
        val hobbiesObserver = captorPostsObserver.firstValue

        // when
        hobbiesObserver.onSuccess(HobbyValidationAction.GeneralError)

        // then
        assertEquals("Network Status is Error", NetworkStatus.Error(null), hobbyViewModel.mutableScreenState.value?.networkStatus)
        assertEquals("ViewModel has no hobbies", emptyList<Hobby>(), hobbyViewModel.mutableScreenState.value?.hobbies)
    }

    @Test
    fun `Should be in error state when get Hobbies return network exception`() {
        //given
        hobbyViewModel.getHobbies()
        val captorPostsObserver = argumentCaptor<HobbyViewModel.HobbiesObserver>()
        verify(mockGetHobbiesUseCase).execute(captorPostsObserver.capture(), isNull())
        val hobbiesObserver = captorPostsObserver.firstValue

        // when
        hobbiesObserver.onError(mockNetworkException)

        // then
        assertEquals("Network Status is Network Error", NetworkStatus.NoConnectionError, hobbyViewModel.mutableScreenState.value?.networkStatus)
        assertEquals("ViewModel has no hobbies", emptyList<Hobby>(), hobbyViewModel.mutableScreenState.value?.hobbies)
    }

    @Test
    fun `Should be in no items state when get Hobbies return no hobbies`() {
        //given
        hobbyViewModel.getHobbies()
        val captorPostsObserver = argumentCaptor<HobbyViewModel.HobbiesObserver>()
        verify(mockGetHobbiesUseCase).execute(captorPostsObserver.capture(), isNull())
        val hobbiesObserver = captorPostsObserver.firstValue

        // when
        hobbiesObserver.onSuccess(HobbyValidationAction.NoHobbies)

        // then
        assertEquals("Network Status is NoItems", NetworkStatus.NoItems, hobbyViewModel.mutableScreenState.value?.networkStatus)
        assertEquals("ViewModel has no posts", emptyList<Hobby>(), hobbyViewModel.mutableScreenState.value?.hobbies)
    }

    @Test
    fun `Should be in success state when get Hobbies return hobbies`() {
        //given
        whenever(mockHobbyValidationModel.hobbies).thenReturn(listOf(mockHobby))
        hobbyViewModel.getHobbies()
        val captorPostsObserver = argumentCaptor<HobbyViewModel.HobbiesObserver>()
        verify(mockGetHobbiesUseCase).execute(captorPostsObserver.capture(), isNull())
        val hobbiesObserver = captorPostsObserver.firstValue

        // when
        hobbiesObserver.onSuccess(HobbyValidationAction.HobbiesDownloaded(mockHobbyValidationModel))

        // then
        assertEquals("Network Status is Success", NetworkStatus.Success, hobbyViewModel.mutableScreenState.value?.networkStatus)
        assertEquals("ViewModel has exactly same amount of hobbies", listOf(mockHobby), hobbyViewModel.mutableScreenState.value?.hobbies)
    }
}
