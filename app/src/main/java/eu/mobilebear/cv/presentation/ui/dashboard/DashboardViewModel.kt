package eu.mobilebear.cv.presentation.ui.dashboard

import androidx.annotation.MainThread
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import eu.mobilebear.cv.domain.interactor.GetCompaniesUseCase
import eu.mobilebear.cv.domain.model.CompanyValidationAction
import eu.mobilebear.cv.networking.response.Company
import eu.mobilebear.cv.util.state.NetworkStatus
import io.reactivex.observers.DisposableSingleObserver

class DashboardViewModel constructor(private val getCompaniesUseCase: GetCompaniesUseCase) : ViewModel() {

    @VisibleForTesting
    internal val mutableScreenState: MutableLiveData<ScreenState> = MutableLiveData()

    val companies: LiveData<ScreenState> by lazy { mutableScreenState }

    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    public override fun onCleared() {
        super.onCleared()
        getCompaniesUseCase.dispose()
    }

    @MainThread
    fun getCompanies() {
        mutableScreenState.value = ScreenState(emptyList(), NetworkStatus.Running)
        getCompaniesUseCase.execute(CompaniesObserver())
    }

    @VisibleForTesting
    inner class CompaniesObserver : DisposableSingleObserver<CompanyValidationAction>() {
        override fun onSuccess(action: CompanyValidationAction) {
            when (action) {
                is CompanyValidationAction.CompaniesDownloaded -> showCompanies(action.companyValidationModel.companies)
                is CompanyValidationAction.NoCompanies -> showNoCompaniesInfo()
                is CompanyValidationAction.GeneralError -> showError(null)
            }
        }

        override fun onError(error: Throwable) {
            showError(error)
        }
    }

    private fun showCompanies(companies: List<Company>) {
        mutableScreenState.value = ScreenState(
            companies = companies,
            networkStatus = NetworkStatus.Success
        )
    }

    private fun showNoCompaniesInfo() {
        mutableScreenState.value = ScreenState(
            companies = emptyList(),
            networkStatus = NetworkStatus.NoItems
        )
    }

    private fun showError(e: Throwable?) {
        val posts = mutableScreenState.value?.companies
        mutableScreenState.value = ScreenState(
            posts,
            NetworkStatus.error(e)
        )
    }

    data class ScreenState(
        val companies: List<Company>?,
        val networkStatus: NetworkStatus
    )
}