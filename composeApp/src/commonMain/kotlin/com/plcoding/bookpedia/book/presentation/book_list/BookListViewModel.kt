package com.plcoding.bookpedia.book.presentation.book_list

import androidx.lifecycle.ViewModel
import com.plcoding.bookpedia.book.domain.Book
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random

class BookListViewModel : ViewModel() {
    private val _state = MutableStateFlow(BookListState(
        searchResult =
            (1..100).map {
                Book(
                    id = "$it",
                    title = "Book $it",
                    imageUrl = "",
                    authors = emptyList(),
                    description = "",
                    languages = emptyList(),
                    firstPublishYear = "",
                    averageRating = Random(10).nextDouble(),
                    ratingCount = 5,
                    numPages = 100,
                    numEditions = 10
                )
            }
    ))
    val state = _state.asStateFlow()

    fun onAction(action: BookListAction){
        when(action){
           is BookListAction.OnBookClick -> {
                _state.value = _state.value.copy()
           }
           is BookListAction.OnQueryChange -> {
               _state.update {
                   it.copy(searchQuery = action.query)
               }
           }
           is BookListAction.OnTabSelected -> {
               _state.update {
                   it.copy(
                      selectedTabIndex = action.index
                   )
               }
           }
        }
    }
}