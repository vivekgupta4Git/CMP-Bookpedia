package com.plcoding.bookpedia.book.presentation.book_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.plcoding.bookpedia.book.domain.Book

@Composable
fun BookList(
    books : List<Book>,
    onBookClick : (Book) -> Unit,
    scrollState : LazyListState = rememberLazyListState(),
    modifier: Modifier = Modifier)
{
    LazyColumn(
        modifier = modifier,
        state = scrollState,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(books,
            key = { it.id}){
            book ->
            BookListItem(book,
                modifier = Modifier
                    .widthIn(max = 700.dp)
                    .fillMaxWidth().padding(horizontal = 16.dp),
                onClick = { onBookClick(book) }
            )
        }
    }

}