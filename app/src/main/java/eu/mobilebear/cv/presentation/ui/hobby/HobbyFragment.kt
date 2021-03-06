package eu.mobilebear.cv.presentation.ui.hobby

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import eu.mobilebear.cv.R
import eu.mobilebear.cv.presentation.ui.hobby.adapter.HobbyAdapter
import eu.mobilebear.cv.util.state.NetworkStatus
import kotlinx.android.synthetic.main.fragment_dashboard.view.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.view_status.*
import kotlinx.android.synthetic.main.view_status.view.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class HobbyFragment : Fragment() {

    private val hobbyViewModel: HobbyViewModel by viewModel()
    private val hobbyAdapter: HobbyAdapter by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)
        initView(rootView)
        hobbyViewModel.hobbies.observe(viewLifecycleOwner, ScreenStateObserver())
        hobbyViewModel.getHobbies()
        return rootView
    }

    private fun initView(rootView: View) {
        val layoutManager = LinearLayoutManager(context)
        rootView.hobbiesRecyclerView.adapter = hobbyAdapter
        rootView.hobbiesRecyclerView.layoutManager = layoutManager
        rootView.retryButton.setOnClickListener { hobbyViewModel.getHobbies() }
    }

    private fun updateViewForSuccessNetworkStatus(screenState: HobbyViewModel.ScreenState) {
        hideLoadingView()
        hobbyAdapter.submitList(screenState.hobbies)
    }

    private fun updateViewForRunningNetworkStatus() {
        showLoadingView()
        hideErrors()
    }

    private fun updateViewForErrorNetworkStatus() {
        hideLoadingView()
        showGeneralError()
    }

    private fun updateViewForNoConnectionErrorNetworkStatus() {
        hideLoadingView()
        showNetworkError()
    }

    private fun updateViewForNoItemsNetworkStatus() {
        hideLoadingView()
        showNoItems()
    }

    private fun showLoadingView() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideLoadingView() {
        progressBar.visibility = View.GONE
    }

    private fun showNoItems(){
        emptyList.visibility = View.VISIBLE
        errorDescription.text = getString(R.string.no_items)
        errorDescription.visibility = View.VISIBLE
        retryButton.visibility = View.VISIBLE
    }

    private fun hideErrors() {
        networkError.visibility = View.GONE
        generalError.visibility = View.GONE
        errorDescription.visibility = View.GONE
        emptyList.visibility = View.GONE
        retryButton.visibility = View.GONE
    }

    private fun showNetworkError() {
        generalError.visibility = View.GONE
        networkError.visibility = View.VISIBLE
        errorDescription.text = getString(R.string.network_error)
        errorDescription.visibility = View.VISIBLE
        retryButton.visibility = View.VISIBLE
    }

    private fun showGeneralError() {
        generalError.visibility = View.VISIBLE
        networkError.visibility = View.GONE
        errorDescription.text = getString(R.string.something_went_wrong)
        errorDescription.visibility = View.VISIBLE
        retryButton.visibility = View.VISIBLE
    }

    private inner class ScreenStateObserver : Observer<HobbyViewModel.ScreenState> {

        override fun onChanged(screenState: HobbyViewModel.ScreenState?) {
            Timber.d("Came back with status:%s", screenState)
            screenState ?: return

            when (screenState.networkStatus) {
                NetworkStatus.Running -> {
                    updateViewForRunningNetworkStatus()
                }
                NetworkStatus.Success -> {
                    updateViewForSuccessNetworkStatus(screenState)
                }
                NetworkStatus.NoConnectionError -> {
                    updateViewForNoConnectionErrorNetworkStatus()
                }

                NetworkStatus.NoItems -> {
                    updateViewForNoItemsNetworkStatus()
                }

                is NetworkStatus.Error -> {
                    updateViewForErrorNetworkStatus()
                }
            }
        }
    }
}
