package com.utsman.libraries.sharedui

import androidx.compose.ui.graphics.vector.ImageVector
import com.utsman.libraries.sharedui.iconpack.IcFavoriteFill
import com.utsman.libraries.sharedui.iconpack.IcFavoriteOutline
import kotlin.collections.List as ____KtList

public object IconPack

private var __AllIcons: ____KtList<ImageVector>? = null

public val IconPack.AllIcons: ____KtList<ImageVector>
  get() {
    if (__AllIcons != null) {
      return __AllIcons!!
    }
    __AllIcons= listOf(IcFavoriteFill, IcFavoriteOutline)
    return __AllIcons!!
  }
