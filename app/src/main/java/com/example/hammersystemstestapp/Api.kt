package com.example.hammersystemstestapp

import com.example.hammersystemstestapp.beer.Beer
import retrofit2.Response
import retrofit2.http.GET

interface Api {
    @GET("beers")
    suspend fun getBeers(): Response<List<Beer>>

    @GET("beers?brewed_before=11-1990")
    suspend fun getOldBeers(): Response<List<Beer>>
}