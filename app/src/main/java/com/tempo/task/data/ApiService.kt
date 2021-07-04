package com.tempo.task.data

import android.provider.ContactsContract.CommonDataKinds.Note
import io.reactivex.Single

import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {
    @GET("everything")
    fun getNews(@Query("q")searchText:String,@Query("apiKey")apiKey:String,
    @Query("page")page:Int,@Query("pageSize")pageSize:Int): Single<Resource>
}