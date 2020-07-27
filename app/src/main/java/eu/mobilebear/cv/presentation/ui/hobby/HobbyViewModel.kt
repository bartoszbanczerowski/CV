package eu.mobilebear.cv.presentation.ui.hobby

import androidx.annotation.MainThread
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import eu.mobilebear.cv.domain.interactor.GetHobbiesUseCase
import eu.mobilebear.cv.domain.model.HobbyValidationAction
import eu.mobilebear.cv.networking.response.Hobby
import eu.mobilebear.cv.util.state.NetworkStatus
import io.reactivex.observers.DisposableSingleObserver

class HobbyViewModel constructor(private val getHobbiesUseCase: GetHobbiesUseCase) : ViewModel() {

    @VisibleForTesting
    internal val mutableScreenState: MutableLiveData<ScreenState> = MutableLiveData()

    val hobbies: LiveData<ScreenState> by lazy { mutableScreenState }

    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    public override fun onCleared() {
        super.onCleared()
        getHobbiesUseCase.dispose()
    }

    @MainThread
    fun getHobbies() {
        mutableScreenState.value = ScreenState(emptyList(), NetworkStatus.Running)
        getHobbiesUseCase.execute(HobbiesObserver())
    }

    @VisibleForTesting
    inner class HobbiesObserver : DisposableSingleObserver<HobbyValidationAction>() {
        override fun onSuccess(action: HobbyValidationAction) {
            when (action) {
                is HobbyValidationAction.HobbiesDownloaded -> showHobbies(action.hobbyValidationModel.hobbies)
                is HobbyValidationAction.NoHobbies -> showNoHobbiesInfo()
                is HobbyValidationAction.GeneralError -> showError(null)
            }
        }

        override fun onError(error: Throwable) {
            showError(error)
        }
    }

    private fun showHobbies(hobbies: List<Hobby>) {
        mutableScreenState.value = ScreenState(
            hobbies = hobbies,
            networkStatus = NetworkStatus.Success
        )
    }

    private fun showNoHobbiesInfo() {
        mutableScreenState.value = ScreenState(
            hobbies = emptyList(),
            networkStatus = NetworkStatus.NoItems
        )
    }

    private fun showError(e: Throwable?) {
        val hobbies = mutableScreenState.value?.hobbies
        mutableScreenState.value = ScreenState(
            hobbies,
            NetworkStatus.error(e)
        )
    }

    data class ScreenState(
        val hobbies: List<Hobby>?,
        val networkStatus: NetworkStatus
    )
}