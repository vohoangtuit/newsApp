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
    private var article: List<Article?>? = null

    fun getStatus(): String? {
        return status
    }

    fun setStatus(status: String?) {
        this.status = status
    }

    fun getTotalResult(): Int {
        return totalResult
    }

    fun setTotalResult(totalResult: Int) {
        this.totalResult = totalResult
    }

    fun getArticle(): List<Article?>? {
        return article
    }

    fun setArticle(article: List<Article?>?) {
        this.article = article
    }
}