package vht.com.news.utils

import java.util.*

fun getCountry(): String? {
    val locale = Locale.getDefault()
    val country = locale.country.toString()
    return country.toLowerCase()
}

fun getLanguage(): String? {
    val locale = Locale.getDefault()
    val country = locale.language.toString()
    return country.toLowerCase()
}