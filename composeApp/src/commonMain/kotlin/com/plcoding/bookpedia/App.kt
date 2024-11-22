package com.plcoding.bookpedia

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.plcoding.bookpedia.book.data.DefaultBookRepository
import com.plcoding.bookpedia.book.data.network.KtorRemoteBookDataSource
import com.plcoding.bookpedia.book.presentation.book_list.BookListScreenRoot
import com.plcoding.bookpedia.book.presentation.book_list.BookListViewModel
import com.plcoding.bookpedia.core.data.HttpClientFactory
import io.ktor.client.engine.HttpClientEngine
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(engine: HttpClientEngine) {
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .background(Color.White),
    ) {
        BookListScreenRoot(
            viewModel =
                remember {
                    BookListViewModel(
                        bookRepository =
                            DefaultBookRepository(
                                remoteBookDataSource =
                                    KtorRemoteBookDataSource(
                                        httpClient =
                                            HttpClientFactory.create(
                                                engine = engine,
                                            ),
                                    ),
                            ),
                    )
                },
            onBookClick = {
            },
        )
    }
}
