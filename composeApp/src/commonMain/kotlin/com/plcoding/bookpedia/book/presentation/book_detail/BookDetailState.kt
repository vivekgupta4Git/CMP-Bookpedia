package com.plcoding.bookpedia.book.presentation.book_detail

import com.plcoding.bookpedia.book.domain.Book
import com.plcoding.bookpedia.core.presentation.UiText

data class BookDetailState(
    val isLoading: Boolean = true,
    val isFavorite: Boolean = false,
    val errorMessage: UiText? = null,
    val book: Book? = null
)
