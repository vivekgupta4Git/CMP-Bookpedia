package com.plcoding.bookpedia.book.presentation.book_list

import com.plcoding.bookpedia.book.domain.Book

data class BookListState(
    val searchQuery : String = "Kotlin",
    val searchResult : List<Book> = emptyList(),
    val favoriteBooks : List<Book> = emptyList(),
    val isLoading : Boolean = false,
    val selectedTabIndex: Int = 0,
    val errorMessage : String? =null
)
