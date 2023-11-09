package com.utsman.libraries.core.utils

import android.icu.text.NumberFormat
import java.util.Locale

actual val Double.currency: String get() {
    val locale = Locale("id", "ID")
    val numberFormat = NumberFormat.getCurrencyInstance(locale)
    return numberFormat.format(this)
}