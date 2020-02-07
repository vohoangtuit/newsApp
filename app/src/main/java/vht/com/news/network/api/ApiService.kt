package vht.com.news.network.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import vht.com.news.BuildConfig

class ApiService() {
    companion object {
        private val apiInterface: ApiInterface? = null
        fun getAPIService(): ApiInterface {
            if (apiInterface != null) {
                return apiInterface
            }
            val body = HttpLoggingInterceptor()
            body.level = HttpLoggingInterceptor.Level.BODY
            val httpClient = OkHttpClient.Builder()
           // val client=httpClient.addInterceptor(logging).build()
            httpClient.addInterceptor { chain ->
                val original = chain.request()
                // Request customization: add request headers
                val requestBuilder = original.newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .method(original.method, original.body)
                val request = requestBuilder.build()
                chain.proceed(request)
            }
            val client=httpClient.addInterceptor(body).build()
            val retrofit = Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
            return retrofit.create(ApiInterface::class.java)
        }
    }
}