package wolt.assignment.demo.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import wolt.assignment.demo.models.RestaurantModel


interface RestaurantApi {
    @GET("v1/pages/restaurants")
    fun getRestaurants(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ): Call<RestaurantModel>
}