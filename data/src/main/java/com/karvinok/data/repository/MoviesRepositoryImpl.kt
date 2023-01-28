package com.karvinok.data.repository

import com.karvinok.data.common.BaseError
import com.karvinok.data.common.CacheManager
import com.karvinok.data.common.Either
import com.karvinok.data.common.proceedResponse
import com.karvinok.data.entity.PopularMoviesResponse
import com.karvinok.data.remote.MoviesApi
import java.util.*

class MoviesRepositoryImpl(
    private val api: MoviesApi,
    private val cache: CacheManager
): MoviesRepository {
    override suspend fun requestMovies(page: Int): Either<BaseError, PopularMoviesResponse> {
        return proceedResponse {
            val response = api.requestPopularMovies(
                apiKey = "a2e93c622da1e30ea4a5b8b8e81fa5d2",
                language = Locale.getDefault().language,
                page = page,
            )
            Either.Success(response)
        }
    }
}