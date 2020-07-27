package eu.mobilebear.cv.domain.repository

import eu.mobilebear.cv.domain.model.CompanyValidationModel
import eu.mobilebear.cv.domain.model.HobbyValidationModel
import io.reactivex.Single

interface CVRepository {
    fun requestCompanies(): Single<CompanyValidationModel>
    fun requestHobbies(): Single<HobbyValidationModel>
}
