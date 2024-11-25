package com.plcoding.bookpedia.book.presentation.book_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.plcoding.bookpedia.app.Route
import com.plcoding.bookpedia.book.domain.BookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class BookDetailViewModel(
    private val bookRepository: BookRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _bookDetailState = MutableStateFlow(BookDetailState())
    val bookDetailState = _bookDetailState
        .onStart {
            observeFavoriteStatus()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _bookDetailState.value
        )
    private val bookId = savedStateHandle.toRoute<Route.BookDetail>().id

    fun onAction(bookDetailAction: BookDetailAction) {
        when (bookDetailAction) {
            BookDetailAction.OnFavoriteClick -> {
                viewModelScope.launch {
                    if (bookDetailState.value.isFavorite) {
                        bookRepository.deleteFavoriteBookById(bookId)

                    } else
                        _bookDetailState.value.book?.let {
                            bookRepository.markAsFavorite(it)

                        }
                }

            }

            is BookDetailAction.OnSelectedBookChange -> {
                _bookDetailState.value = _bookDetailState.value.copy(
                    book = bookDetailAction.book
                )
            }

            else -> Unit
        }
    }

    private fun observeFavoriteStatus() {
        bookRepository.isBookFavorite(bookId).onEach {
            _bookDetailState.value = _bookDetailState.value.copy(
                isFavorite = it
            )
        }.launchIn(viewModelScope)
    }
}