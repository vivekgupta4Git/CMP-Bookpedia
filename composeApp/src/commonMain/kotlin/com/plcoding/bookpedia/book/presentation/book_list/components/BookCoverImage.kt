package com.plcoding.bookpedia.book.presentation.book_list.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cmp_bookpedia.composeapp.generated.resources.Res
import cmp_bookpedia.composeapp.generated.resources.book_error_2
import coil3.compose.rememberAsyncImagePainter
import com.plcoding.bookpedia.book.domain.Book
import org.jetbrains.compose.resources.painterResource

@Composable
fun BookCoverImage(
    modifier: Modifier = Modifier,
    book: Book
) {
    Box(
        modifier = Modifier
            .height(100.dp),
        contentAlignment = Alignment.Center
    ) {
        var imageLoadResult by remember {
            mutableStateOf<Result<Painter>?>(null)
        }
        val painter = rememberAsyncImagePainter(
            model = book.imageUrl,
            onSuccess = { result ->
                if (result.painter.intrinsicSize.width > 1 && result.painter.intrinsicSize.height > 1)
                    imageLoadResult = Result.success(result.painter)
                else
                    imageLoadResult = Result.failure(Throwable("Invalid image size"))
            },
            onError = {
                imageLoadResult = Result.failure(it.result.throwable)
            }
        )
        when (val result = imageLoadResult) {
            null -> CircularProgressIndicator()
            else -> {
                Image(
                    painter = if (result.isSuccess)
                        painter
                    else
                        painterResource(Res.drawable.book_error_2),
                    contentDescription = book.title,
                    contentScale = if (result.isSuccess) {
                        ContentScale.Crop
                    } else {
                        ContentScale.Fit
                    },
                    modifier = Modifier
                        .aspectRatio(
                            0.65f,
                            matchHeightConstraintsFirst = true
                        ),
                )
            }
        }
    }
}