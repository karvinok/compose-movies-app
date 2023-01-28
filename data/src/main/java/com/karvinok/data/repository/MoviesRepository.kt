package com.karvinok.data.repository

import com.karvinok.data.common.BaseError
import com.karvinok.data.common.Either
import com.karvinok.data.entity.PopularMoviesResponse

interface MoviesRepository {
    suspend fun requestMovies(page: Int): Either<BaseError, PopularMoviesResponse>
}