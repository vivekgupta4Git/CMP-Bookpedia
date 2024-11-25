package com.plcoding.bookpedia.book.data

import androidx.sqlite.SQLiteException
import com.plcoding.bookpedia.book.data.database.FavoriteBokDao
import com.plcoding.bookpedia.book.data.network.RemoteBookDataSource
import com.plcoding.bookpedia.book.domain.Book
import com.plcoding.bookpedia.book.domain.BookRepository
import com.plcoding.bookpedia.book.mappers.toBook
import com.plcoding.bookpedia.book.mappers.toBookEntity
import com.plcoding.bookpedia.book.mappers.toDomainBookList
import com.plcoding.bookpedia.core.domain.DataError
import com.plcoding.bookpedia.core.domain.EmptyResult
import com.plcoding.bookpedia.core.domain.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultBookRepository(
    private val remoteBookDataSource: RemoteBookDataSource,
    private val dao: FavoriteBokDao
) : BookRepository{
    override suspend fun searchBooks(query: String, limit: Int?)
            : Result<List<Book>, DataError.Remote> {
        return remoteBookDataSource
            .searchBooks(query, limit)
            .toDomainBookList()
    }

    override fun getFavoriteBooks(): Flow<List<Book>> {
      return  dao.getFavouriteBooks().map {
            entities -> entities.map { it.toBook() }
        }
    }

    override fun isBookFavorite(id: String): Flow<Boolean> {
        return dao.getFavouriteBooks().map {
                entities -> entities.any { it.id == id }}
    }

    override suspend fun markAsFavorite(book: Book): EmptyResult<DataError.Local> {
        try {
            dao.upsert(book.toBookEntity())
            return Result.Success(Unit)
        }catch (ex : SQLiteException){
            return Result.Error(DataError.Local.DATABASE_ERROR)
        }
    }

    override suspend fun deleteFavoriteBookById(id: String): EmptyResult<DataError.Local> {
        try {
            dao.deleteFavoriteBookById(id)
            return Result.Success(Unit)
        }catch (ex : SQLiteException){
            return Result.Error(DataError.Local.DATABASE_ERROR)
        }
    }
}