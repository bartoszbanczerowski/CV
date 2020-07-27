package eu.mobilebear.cv.networking

import eu.mobilebear.cv.networking.response.Company
import eu.mobilebear.cv.networking.response.Hobby
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CVService {

    @GET("hobbies.json")
    fun getHobbies(): Single<Response<List<Hobby>>>

    @GET("companies.json")
    fun getCompanies(): Single<Response<List<Company>>>
}
