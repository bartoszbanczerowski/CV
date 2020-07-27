package eu.mobilebear.cv.domain.model

import androidx.annotation.StringDef
import eu.mobilebear.cv.networking.response.Hobby

data class HobbyValidationModel(
    val hobbies: List<Hobby>,
    @HobbyStatus val status: String
) {
    companion object {
        const val HOBBIES_DOWNLOADED = "HOBBIES_DOWNLOADED"
        const val NO_HOBBIES = "NO_HOBBIES"
        const val GENERAL_ERROR = "GENERAL_LIST"

        @Retention(AnnotationRetention.SOURCE)
        @StringDef(HOBBIES_DOWNLOADED, NO_HOBBIES, GENERAL_ERROR)
        annotation class HobbyStatus
    }
}
