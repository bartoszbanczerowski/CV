package eu.mobilebear.cv.networking.response

import com.squareup.moshi.Json

data class Hobby constructor(
    @Json(name = "name") var name: String = "",
    @Json(name = "description") var description: String = "",
    @Json(name = "url") var url: String = ""
)
