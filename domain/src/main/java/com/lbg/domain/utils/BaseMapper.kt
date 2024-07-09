package com.lbg.domain.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface Mapper<A, K> {
    fun mapFrom(from: A): K
}

fun <A, K> mapFrom(result: Flow<Result<A>>, mapper: Mapper<A, K>): Flow<Result<K>> {
    return result.map {
        when (it) {
            is Result.Success -> Result.Success(mapper.mapFrom(it.data))
            is Result.Error -> Result.Error(it.message, it.code)
            is Result.Loading -> Result.Loading
        }
    }
}
