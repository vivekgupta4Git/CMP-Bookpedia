package com.plcoding.bookpedia.book.domain

import com.plcoding.bookpedia.core.domain.DataError
import com.plcoding.bookpedia.core.domain.Result

interface BookRemoteDataSource {
    suspend fun searchBooks(query: String,
                            limit : Int? = null) :
            Result<List<Book>,DataError.Remote>
}