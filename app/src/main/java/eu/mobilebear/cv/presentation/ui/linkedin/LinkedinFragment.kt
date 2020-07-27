package eu.mobilebear.cv.presentation.ui.linkedin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import eu.mobilebear.cv.R
import org.koin.androidx.viewmodel.ext.android.viewModel


class LinkedinFragment : Fragment() {

    companion object {
        const val LINKEDIN_PROFILE = "https://www.linkedin.com/in/bartosz-ba%C5%84czerowski-9a87aa97/"
    }

    private val linkedinViewModel: LinkedinViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)
        val webView: WebView = root.findViewById(R.id.linkedinWebView)
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()
        webView.loadUrl(LINKEDIN_PROFILE)
        return root
    }
}
