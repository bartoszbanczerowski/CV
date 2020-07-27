package eu.mobilebear.cv.domain.interactor

import eu.mobilebear.cv.domain.interactor.base.SingleUseCase
import eu.mobilebear.cv.domain.model.CompanyValidationAction
import eu.mobilebear.cv.domain.model.CompanyValidationAction.*
import eu.mobilebear.cv.domain.model.CompanyValidationModel
import eu.mobilebear.cv.domain.repository.CVRepository
import eu.mobilebear.cv.rx.RxFactory
import io.reactivex.Single

class GetCompaniesUseCase constructor(
    private val cvRepository: CVRepository,
    rxFactory: RxFactory
) : SingleUseCase<CompanyValidationAction, Unit>(IO_THREAD, rxFactory) {

    override fun buildUseCaseSingle(params: Unit?): Single<CompanyValidationAction> =
        cvRepository.requestCompanies()
            .flatMap { companyValidationModel: CompanyValidationModel -> mapIntoCompanyAction(companyValidationModel) }

    private fun mapIntoCompanyAction(companyValidationModel: CompanyValidationModel): Single<CompanyValidationAction> =
        Single.just(
            when (companyValidationModel.status) {
                CompanyValidationModel.COMPANIES_DOWNLOADED -> CompaniesDownloaded(companyValidationModel)
                CompanyValidationModel.NO_COMPANIES -> NoCompanies
                else -> GeneralError
            }
        )
}
