package eu.mobilebear.cv.domain.model

sealed class HobbyValidationAction {

    class HobbiesDownloaded(val hobbyValidationModel: HobbyValidationModel) : HobbyValidationAction()

    object NoHobbies : HobbyValidationAction()

    object GeneralError : HobbyValidationAction()
}
