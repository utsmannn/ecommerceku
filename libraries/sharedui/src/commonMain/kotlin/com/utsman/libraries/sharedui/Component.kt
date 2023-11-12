package com.utsman.libraries.sharedui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.kamel.core.Resource
import io.kamel.core.config.KamelConfig
import io.kamel.core.config.httpFetcher
import io.kamel.core.config.takeFrom
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import io.kamel.image.config.Default
import io.kamel.image.config.imageBitmapDecoder
import io.kamel.image.config.imageVectorDecoder
import io.kamel.image.config.svgDecoder
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE

val RoundedShape = RoundedCornerShape(12.dp)
val RoundedOnlyTopShape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)

val ColorBackgroundItem = Color.Gray.copy(alpha = 0.2f)

val TextStyle.Companion.Header: TextStyle
    get() = TextStyle.Default.copy(
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    )

val customKamelConfig = KamelConfig {
    takeFrom(KamelConfig.Default)
    imageBitmapCacheSize = 500
    imageVectorDecoder()
    imageBitmapDecoder()
    svgDecoder()

    httpFetcher {
        httpCache(20 * 1024 * 1024  /* 10 MiB */)

        Logging {
            level = LogLevel.INFO
            logger = Logger.SIMPLE
        }
    }
}

val IconFavoriteOutline
    @Composable
    get() = asyncPainterResource("icons/ic_favorite.svg")

val IconFavoriteFill
    @Composable
    get() = asyncPainterResource("icons/ic_favorite_fill.svg")

@Composable
fun ImageRemote(
    url: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit
) {
    val painter = asyncPainterResource(url)

    KamelImage(
        modifier = modifier,
        resource = painter,
        contentDescription = "Image of $url",
        contentScale = contentScale
    )
}

@Composable
fun ImageResource(
    resource: Resource<Painter>,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit
) {

    KamelImage(
        modifier = modifier,
        resource = resource,
        contentDescription = null,
        contentScale = contentScale
    )
}

@Composable
fun ImageResource(
    resourcePath: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit
) {
    val resource = asyncPainterResource(resourcePath)

    KamelImage(
        modifier = modifier,
        resource = resource,
        contentDescription = null,
        contentScale = contentScale
    )
}

@Composable
fun Loading(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun Failure(modifier: Modifier = Modifier, text: String?) {
    Text(
        text = text.orEmpty(),
        modifier = Modifier.padding(12.dp).then(modifier),
        color = Color.Red
    )
}

@Composable
fun ToolbarWithBackNavigation(title: String, onBack: () -> Unit) {
    TopAppBar(
        title = {
            Text(text = title)
        },
        navigationIcon = {
            val vector = Icons.Filled.ArrowBack
            IconButton(
                onClick = {
                    onBack.invoke()
                }
            ) {
                Icon(
                    imageVector = vector,
                    contentDescription = null
                )
            }
        }
    )
}