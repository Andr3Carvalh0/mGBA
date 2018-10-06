package io.mgba.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitWrapper {
    lateinit var retrofit: Retrofit
    val BASE_URL = "https://andr3carvalh0.github.io/Databases/mGBA/"

    fun init() { init(BASE_URL) }
    fun init(baseURL: String) { retrofit = instance(baseURL) }

    private fun instance(baseUrl: String): Retrofit {
        return Retrofit.Builder()
                       .baseUrl(baseUrl)
                       .addConverterFactory(GsonConverterFactory.create())
                       .build()

    }
}
