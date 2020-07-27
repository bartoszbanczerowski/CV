package eu.mobilebear.cv.networking.response

import com.squareup.moshi.Json

data class Company constructor(
    @Json(name = "name") var name: String = "",
    @Json(name = "logo") var logo: String = "",
    @Json(name = "role") var role: String = "",
    @Json(name = "location") var location: String = "",
    @Json(name = "dateFromTo") var dateFromTo: String = "",
    @Json(name = "description") var description: String = "",
    @Json(name = "technologies") var technologies: String = "",
    @Json(name = "googlePlayUrl") var googlePlayUrl: String = ""
)
