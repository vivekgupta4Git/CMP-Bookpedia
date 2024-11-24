package com.plcoding.bookpedia.book.presentation.book_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.plcoding.bookpedia.book.presentation.book_detail.components.BlurredImageBackground
import com.plcoding.bookpedia.book.presentation.book_detail.components.BookChip
import com.plcoding.bookpedia.book.presentation.book_detail.components.ChipSize
import com.plcoding.bookpedia.book.presentation.book_detail.components.TitledContent
import com.plcoding.bookpedia.core.presentation.SandYellow
import kotlin.math.round

@Composable
fun BookDetailScreenRoot(
    bookDetailViewModel: BookDetailViewModel,
    onBackAction: () -> Unit
) {
    val bookDetailState by bookDetailViewModel.bookDetailState.collectAsStateWithLifecycle()
    BookDetailScreen(
        bookDetailState = bookDetailState,
        onAction = { action ->
            when (action) {
                BookDetailAction.OnBackClick -> onBackAction()
                else -> bookDetailViewModel.onAction(action)
            }
        }
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun BookDetailScreen(
    bookDetailState: BookDetailState,
    onAction: (BookDetailAction) -> Unit,
) {
    BlurredImageBackground(
        imageUrl = bookDetailState.book?.imageUrl,
        isFavorite = bookDetailState.isFavorite,
        onFavouriteClick = {
            onAction(BookDetailAction.OnFavoriteClick)
        },
        onBackClick = {
            onAction(BookDetailAction.OnBackClick)
        },
    ) {
        if (bookDetailState.book != null) {
            Column(
                modifier = Modifier
                    .widthIn(max = 700.dp)
                    .fillMaxWidth()
                    .padding(
                        vertical = 16.dp,
                        horizontal = 24.dp
                    )
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = bookDetailState.book.title,
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = bookDetailState.book.authors.joinToString(),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )
                Row(
                    modifier = Modifier.padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    bookDetailState.book.averageRating?.let { rating ->
                        TitledContent("Rating") {
                            BookChip {
                                Text("${round((rating * 10) / 100)}")
                                Icon(
                                    Icons.Default.Star,
                                    contentDescription = "star",
                                    tint = SandYellow
                                )
                            }
                        }
                    }
                    bookDetailState.book.numPages?.let { pages ->
                        TitledContent("Pages") {
                            BookChip {
                                Text("$pages")
                            }
                        }
                    }
                }
                if (bookDetailState.book.languages.isNotEmpty()) {
                    TitledContent("Languages"){
                        FlowRow(
                            modifier = Modifier.padding(8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            bookDetailState.book.languages.forEach {
                                BookChip(
                                    size = ChipSize.SMALL
                                ) {
                                    Text(it)
                                }
                            }
                        }
                    }
                }
            }

        }
    }
}