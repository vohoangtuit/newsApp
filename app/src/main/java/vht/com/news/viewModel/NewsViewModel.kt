package vht.com.news.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import vht.com.news.BuildConfig
import vht.com.news.models.News
import vht.com.news.network.api.ApiService
import vht.com.news.utils.getCountry

class NewsViewModel: BaseViewModel() {

    private var listNewsResponse = MutableLiveData<News>()


    fun newsResponse(): MutableLiveData<News> {
        return listNewsResponse
    }



    fun getListNews() {
        disposables.add(ApiService.getAPIService().getNews(getCountry(),BuildConfig.NEW_API_KEY)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { showLoading(true) }
            .doFinally { showLoading(false) }
            .subscribe(fun(it: News) {
                listNewsResponse.value = it
            }, {
                showFailure(it)
            }))
    }

    override fun onCleared() {
        Log.d("UserViewModel", "onCleared")
        disposables.clear()
    }
}