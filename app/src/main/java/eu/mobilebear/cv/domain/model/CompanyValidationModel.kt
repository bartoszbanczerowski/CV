package eu.mobilebear.cv.domain.model

import androidx.annotation.StringDef
import eu.mobilebear.cv.networking.response.Company

data class CompanyValidationModel(
    val companies: List<Company>,
    @CompanyStatus val status: String
) {
    companion object {
        const val COMPANIES_DOWNLOADED = "COMPANIES_DOWNLOADED"
        const val NO_COMPANIES = "NO_COMPANIES"
        const val GENERAL_ERROR = "GENERAL_LIST"

        @Retention(AnnotationRetention.SOURCE)
        @StringDef(COMPANIES_DOWNLOADED, NO_COMPANIES, GENERAL_ERROR)
        annotation class CompanyStatus
    }
}
