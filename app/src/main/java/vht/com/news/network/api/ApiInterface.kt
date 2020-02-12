package vht.com.news.network.api

import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import vht.com.news.models.News

interface ApiInterface {
    @GET("top-headlines")
    fun getNews(@Query("country") country: String?, @Query("apiKey") apiKey: String?): Observable<News>


    @GET("everything")
    fun searchNews(@Query("q") keyword: String?, @Query("language") language: String?, @Query("sortBy") sortBy: String?, @Query("apiKey") apiKey: String?): Observable<News>
}

