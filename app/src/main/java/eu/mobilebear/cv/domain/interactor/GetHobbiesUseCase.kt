package eu.mobilebear.cv.domain.interactor

import eu.mobilebear.cv.domain.interactor.base.SingleUseCase
import eu.mobilebear.cv.domain.model.HobbyValidationAction
import eu.mobilebear.cv.domain.model.HobbyValidationModel
import eu.mobilebear.cv.domain.repository.CVRepository
import eu.mobilebear.cv.rx.RxFactory
import io.reactivex.Single

class GetHobbiesUseCase constructor(
    private val cvRepository: CVRepository,
    rxFactory: RxFactory
) : SingleUseCase<HobbyValidationAction, Unit>(IO_THREAD, rxFactory) {

    override fun buildUseCaseSingle(params: Unit?): Single<HobbyValidationAction> =
        cvRepository.requestHobbies()
            .flatMap { hobbyValidationModel: HobbyValidationModel -> mapIntoHobbyAction(hobbyValidationModel) }

    private fun mapIntoHobbyAction(hobbyValidationModel: HobbyValidationModel): Single<HobbyValidationAction> =
        Single.just(
            when (hobbyValidationModel.status) {
                HobbyValidationModel.HOBBIES_DOWNLOADED -> HobbyValidationAction.HobbiesDownloaded(hobbyValidationModel)
                HobbyValidationModel.NO_HOBBIES -> HobbyValidationAction.NoHobbies
                else -> HobbyValidationAction.GeneralError
            }
        )
}
