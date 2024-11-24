package com.plcoding.bookpedia.book.presentation.book_detail.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cmp_bookpedia.composeapp.generated.resources.Res
import cmp_bookpedia.composeapp.generated.resources.book_error_2
import coil3.compose.rememberAsyncImagePainter
import com.plcoding.bookpedia.core.presentation.DarkBlue
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource

@Composable
fun BlurredImageBackground(
    imageUrl: String?,
    isFavorite: Boolean,
    onFavouriteClick: () -> Unit,
    onBackClick: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    val blurImage = remember { Animatable(0.1f) }
    val whiteBackground = remember { Animatable(0.9f) }
    LaunchedEffect(true){
        launch { blurImage.animateTo(0.3f, initialVelocity = 0.5f,
            animationSpec = tween(500),

        ) }
        launch { whiteBackground.animateTo(0.7f, initialVelocity = 0.8f,
            animationSpec = tween(1000)
        ) }
    }
    var imageLoadResult by remember {
        mutableStateOf<Result<Painter>?>(null)
    }
    val painter = rememberAsyncImagePainter(
        model = imageUrl,
        onSuccess = {
            val size = it.painter.intrinsicSize
            if (size.width > 1 && size.height > 1)
                imageLoadResult = Result.success(it.painter)
        },
        onError = {
            imageLoadResult = Result.failure(it.result.throwable)
        }
    )
    Box {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .weight(blurImage.value)
                    .fillMaxWidth()
                    .background(DarkBlue)
            ) {
                imageLoadResult?.getOrNull()?.let { painter ->
                    Image(
                        painter = painter,
                        contentDescription = "book cover",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .blur(10.dp)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .weight(whiteBackground.value)
                    .fillMaxSize()
                    .background(Color.Gray)
            ) {
                //white background
            }

        }
        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 15.dp, start = 15.dp)
                .statusBarsPadding()
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                contentDescription = "go back",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.fillMaxHeight(0.12f))

            ElevatedCard(
                modifier = Modifier
                    .height(250.dp)
                    .aspectRatio(2 / 3f),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.elevatedCardColors(
                    containerColor = Color.Transparent
                ),
                elevation = CardDefaults.elevatedCardElevation(
                    defaultElevation = 10.dp
                )
            ) {
                AnimatedContent(
                    targetState = imageLoadResult,
                    modifier = Modifier.fillMaxSize()
                ) { result ->
                    when (result) {
                        null -> Unit
                        else -> {
                            Box {
                                Image(
                                    painter =
                                    if (result.isSuccess) painter else
                                        painterResource(Res.drawable.book_error_2),
                                    contentDescription = "book cover",
                                    contentScale = ContentScale.Fit,
                                    modifier = Modifier.fillMaxSize()
                                )
                                IconButton(
                                    onClick = onFavouriteClick,
                                    modifier = Modifier
                                        .align(Alignment.BottomEnd)
                                        .padding(15.dp)

                                ) {
                                    Icon(
                                        imageVector =
                                        Icons.Default.Favorite,
                                        contentDescription = "favorite",
                                        tint = if (isFavorite) Color.Red else Color.Gray
                                    )
                                }
                            }
                        }
                    }
                }
            }
            content()
        }
    }
}