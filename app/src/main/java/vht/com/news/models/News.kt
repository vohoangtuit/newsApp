package vht.com.news.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class News {
    @SerializedName("status")
    @Expose
    private var status: String? = null

    @SerializedName("totalResult")
    @Expose
    private var totalResult = 0

    @SerializedName("articles")
    @Expose
    var article: ArrayList<Article> = ArrayList()


}