package com.utsman.libraries.sharedui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

val RoundedShape = RoundedCornerShape(12.dp)
val RoundedOnlyTopShape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)

val ColorBackgroundItem = Color.Gray.copy(alpha = 0.2f)

@Composable
fun ImageRemote(url: String, modifier: Modifier = Modifier, contentScale: ContentScale = ContentScale.Fit) {
    val painter = asyncPainterResource(url)

    KamelImage(
        modifier = modifier,
        resource = painter,
        contentDescription = "Image of $url",
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
    Text("error -> $text")
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