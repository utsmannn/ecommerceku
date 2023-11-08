package com.utsman.libraries.sharedui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.utsman.apis.product.model.ProductItemList

@Composable
fun ProductItem(productListItem: ProductItemList, onClickItem: (ProductItemList) -> Unit = {}) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(2/3f)
            .padding(6.dp)
            .background(color = ColorBackgroundItem, shape = RoundedShape)
            .clickable {
                onClickItem.invoke(productListItem)
            }
    ) {
        ImageRemote(
            url = productListItem.images,
            modifier = Modifier.fillMaxWidth()
                .aspectRatio(1/1f)
                .background(color = Color.White, shape = RoundedOnlyTopShape)
                .clip(shape = RoundedOnlyTopShape)
        )

        Column(
            modifier = Modifier.padding(6.dp)
        ) {
            Text(
                text = productListItem.name,
                modifier = Modifier.fillMaxWidth(),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = productListItem.price.toString(),
                fontWeight = FontWeight.Black
            )
        }
    }
}