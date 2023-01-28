package com.karvinok.data.remote

import com.karvinok.data.entity.PopularMoviesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {

@GET("movie/popular")
    suspend fun requestPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): PopularMoviesResponse
}
