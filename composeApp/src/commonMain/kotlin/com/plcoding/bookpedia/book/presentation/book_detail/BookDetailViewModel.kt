package com.plcoding.bookpedia.book.presentation.book_detail

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class BookDetailViewModel : ViewModel() {
    private val _bookDetailState = MutableStateFlow(BookDetailState())
    val bookDetailState = _bookDetailState.asStateFlow()

    fun onAction(bookDetailAction: BookDetailAction) {
        when (bookDetailAction) {
            BookDetailAction.OnFavoriteClick -> {
                _bookDetailState.value = _bookDetailState.value.copy(
                    isFavorite = !_bookDetailState.value.isFavorite
                )
            }

            is BookDetailAction.OnSelectedBookChange -> {
                _bookDetailState.value = _bookDetailState.value.copy(
                    book = bookDetailAction.book
                )
            }

            else -> Unit
        }
    }
}