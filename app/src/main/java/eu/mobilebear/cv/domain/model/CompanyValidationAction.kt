package eu.mobilebear.cv.domain.model

sealed class CompanyValidationAction {

    class CompaniesDownloaded(val companyValidationModel: CompanyValidationModel) : CompanyValidationAction()

    object NoCompanies : CompanyValidationAction()

    object GeneralError : CompanyValidationAction()
}
