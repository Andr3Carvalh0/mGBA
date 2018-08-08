package io.mgba.data.remote.interfaces

import io.mgba.data.remote.dtos.GameJSON
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface IRequest {

    @GET("Games_{lang}/{md5}.json")
    fun getGameInformation(@Path("md5") md5: String, @Path("lang") lang: String): Call<GameJSON>

    companion object {

        val BASE_URL = "https://andr3carvalh0.github.io/Databases/mGBA/"
    }
}
