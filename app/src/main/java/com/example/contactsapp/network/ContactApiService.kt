package com.example.contactsapp.network

import com.example.contactsapp.domain.ContactProperty
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Possible Query?
 * https://randomuser.me/api/?results=1&inc=name,gender,phone,email,location&noinfo
 * https://randomuser.me/api/?results=3&inc=name,gender,phone,email,location&nat=us&noinfo
 */

private const val BASE_URL = "https://randomuser.me/api/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()


interface ContactApiService {
    @GET("?inc=name,gender,phone,email,location&nat=us&noinfo")
    suspend fun getContactList(@Query("results") results: Int): NetworkContactContainer
}

object ContactApi {
    val retrofitService: ContactApiService by lazy {
        retrofit.create(ContactApiService::class.java)
    }
}