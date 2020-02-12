package vht.com.news.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import vht.com.news.BuildConfig
import vht.com.news.constant.Constant.Companion.COUNTRY_CODE
import vht.com.news.constant.Constant.Companion.LANGUAGE
import vht.com.news.models.Article
import vht.com.news.models.News
import vht.com.news.network.api.ApiService
import vht.com.news.utils.Utils.getCountry

class NewsViewModel: BaseViewModel() {

    private var listNewsResponse = MutableLiveData<News>()
    private var searchNewsResponse = MutableLiveData<News>()


    fun newsResponse(): MutableLiveData<News> {
        return listNewsResponse
    }
    fun searchResponse(): MutableLiveData<News> {
        return searchNewsResponse
    }


    fun getListNews(sfreshLayout : SwipeRefreshLayout) {
        sfreshLayout.setRefreshing(true)
        disposables.add(ApiService.getAPIService().getNews(COUNTRY_CODE,BuildConfig.NEW_API_KEY)//getCountry()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { showLoading(false) }
            .doFinally { showLoading(false) }
            .subscribe(fun(it: News) {
                listNewsResponse.value = it
                sfreshLayout.setRefreshing(false)
            }, {
                showFailure(it)
                sfreshLayout.setRefreshing(false)
            }))
    }
    fun searchNews(keyword: String) {
        disposables.add(ApiService.getAPIService().searchNews(keyword, LANGUAGE, "publishedAt",BuildConfig.NEW_API_KEY)//getCountry()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { showLoading(true) }
            .doFinally { showLoading(false) }
            .subscribe(fun(it: News) {
                searchNewsResponse.value = it
            }, {
                showFailure(it)
            }))
    }

    override fun onCleared() {
        disposables.clear()
    }
}