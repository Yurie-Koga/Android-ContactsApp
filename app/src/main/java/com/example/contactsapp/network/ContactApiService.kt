package com.example.contactsapp.network

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

/**
 * Possible Query?
 * https://randomuser.me/api/?results=1&inc=name,gender,phone,email,location&noinfo
 */

private const val BASE_URL = "https://randomuser.me/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()


interface ContactApiService {
    @GET("api")
    fun getProperties(): Call<String>
}

object ContactApi {
    val retrofitService: ContactApiService by lazy {
        retrofit.create(ContactApiService::class.java)
    }
}