package vht.com.news.utils

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import java.util.*
object Utils {
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

    var vibrantLightColorList = arrayOf(
        ColorDrawable(Color.parseColor("#ffeead")),
        ColorDrawable(Color.parseColor("#93cfb3")),
        ColorDrawable(Color.parseColor("#fd7a7a")),
        ColorDrawable(Color.parseColor("#faca5f")),
        ColorDrawable(Color.parseColor("#1ba798")),
        ColorDrawable(Color.parseColor("#6aa9ae")),
        ColorDrawable(Color.parseColor("#ffbf27")),
        ColorDrawable(Color.parseColor("#d93947"))
    )

    fun getRandomDrawbleColor(): ColorDrawable? {
        val idx = Random().nextInt(vibrantLightColorList.size)
        return vibrantLightColorList[idx]
    }
    fun glideRequestOptions(): RequestOptions{
        val requestOptions = RequestOptions()
        requestOptions.placeholder(getRandomDrawbleColor())
        requestOptions.error(getRandomDrawbleColor())
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL)
        requestOptions.centerCrop()
        return requestOptions
    }

    fun isEmpty(str: String?): Boolean {
        return str == null || str.isEmpty()
    }
}
