package eu.mobilebear.cv.data.repository

import eu.mobilebear.cv.domain.repository.CVRepository
import eu.mobilebear.cv.domain.model.CompanyValidationModel
import eu.mobilebear.cv.domain.model.HobbyValidationModel
import eu.mobilebear.cv.networking.CVService
import eu.mobilebear.cv.networking.response.Company
import eu.mobilebear.cv.networking.response.Hobby
import eu.mobilebear.cv.util.ConnectionChecker
import eu.mobilebear.cv.util.NetworkException
import io.reactivex.Single
import retrofit2.Response

class CVRepositoryImpl
constructor(
    private val cvService: CVService,
    private val connectionChecker: ConnectionChecker
) : CVRepository {

    override fun requestCompanies(): Single<CompanyValidationModel> {
        if (!connectionChecker.isConnected) return Single.error(NetworkException())
        return cvService.getCompanies()
            .map { response -> mapCompaniesResponse(response) }
            .onErrorReturn { mapCompaniesError() }
    }

    override fun requestHobbies(): Single<HobbyValidationModel> {
        if (!connectionChecker.isConnected) return Single.error(NetworkException())
        return cvService.getHobbies()
            .map { response -> mapHobbiesResponse(response) }
            .onErrorReturn { mapHobbiesError() }
    }


    private fun mapCompaniesResponse(response: Response<List<Company>>): CompanyValidationModel {
        val isResponseSuccessful = response.isSuccessful && response.body() != null

        return if (isResponseSuccessful && response.body()!!.isNotEmpty()) {
            CompanyValidationModel(response.body()!!, CompanyValidationModel.COMPANIES_DOWNLOADED)
        } else if(isResponseSuccessful) {
            CompanyValidationModel(response.body()!!, CompanyValidationModel.NO_COMPANIES)
        } else {
            mapCompaniesError()
        }
    }

    private fun mapCompaniesError(): CompanyValidationModel =
        CompanyValidationModel(emptyList(), CompanyValidationModel.GENERAL_ERROR)

    private fun mapHobbiesResponse(response: Response<List<Hobby>>): HobbyValidationModel {
        val isResponseSuccessful = response.isSuccessful && response.body() != null

        return if (isResponseSuccessful) {
            HobbyValidationModel(response.body()!!, HobbyValidationModel.HOBBIES_DOWNLOADED)
        } else {
            mapHobbiesError()
        }
    }

    private fun mapHobbiesError(): HobbyValidationModel =
        HobbyValidationModel(emptyList(), HobbyValidationModel.GENERAL_ERROR)
}
