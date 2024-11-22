package com.plcoding.bookpedia.book.mappers

import com.plcoding.bookpedia.book.data.dto.SearchResponseDto
import com.plcoding.bookpedia.book.data.dto.SearchedBookDto
import com.plcoding.bookpedia.book.domain.Book
import com.plcoding.bookpedia.core.domain.DataError
import com.plcoding.bookpedia.core.domain.Result
import com.plcoding.bookpedia.core.domain.map

fun SearchedBookDto.toBook(): Book {
    return Book(
        id = id,
        title = title,
        imageUrl = if(coverKey != null) "https://covers.openlibrary.org/b/olid/${coverKey}-L.jpg"
                   else {
                       "https://covers.openlibrary.org/b/id/${coverAlternativeKey}-L.jpg"
        },
       authors = authorNames ?: emptyList(),
        description = null,
        languages = languages ?: emptyList(),
        firstPublishYear = firstPublishYear.toString(),
        averageRating = ratingsAverage,
        ratingCount = ratingsCount,
        numPages = numberOfPages,
        numEditions = editionCount ?: 0)
}

fun List<SearchedBookDto>.toBooks(): List<Book> = map {
    println(it.id)
    it.toBook()
}

fun Result<SearchResponseDto, DataError.Remote>.toDomainBookList() =
    map { it.results.toBooks() }