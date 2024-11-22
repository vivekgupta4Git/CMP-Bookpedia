package com.plcoding.bookpedia.book.data

import com.plcoding.bookpedia.book.data.network.RemoteBookDataSource
import com.plcoding.bookpedia.book.domain.Book
import com.plcoding.bookpedia.book.domain.BookRepository
import com.plcoding.bookpedia.book.mappers.toBook
import com.plcoding.bookpedia.book.mappers.toBooks
import com.plcoding.bookpedia.book.mappers.toDomainBookList
import com.plcoding.bookpedia.core.domain.DataError
import com.plcoding.bookpedia.core.domain.Result
import com.plcoding.bookpedia.core.domain.map

class DefaultBookRepository(
    private val remoteBookDataSource: RemoteBookDataSource
) : BookRepository{
    override suspend fun searchBooks(query: String, limit: Int?)
            : Result<List<Book>, DataError.Remote> {
        return remoteBookDataSource
            .searchBooks(query, limit)
            .toDomainBookList()
    }
}