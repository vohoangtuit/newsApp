package vht.com.news.viewModel

import androidx.annotation.Nullable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonSyntaxException
import io.reactivex.disposables.CompositeDisposable
import retrofit2.HttpException
import vht.com.news.models.response.BaseResponseModel
import vht.com.news.network.Event
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.HttpsURLConnection

 open class BaseViewModel : ViewModel() {
     val eventLoading = MutableLiveData<Event<Boolean>>()
     val eventError = MutableLiveData<Event<BaseResponseModel>>()
     val eventFailure = MutableLiveData<Event<Throwable>>()
     val disposables = CompositeDisposable()

     fun showLoading(value: Boolean) {
         eventLoading.value = Event(value)
     }

     fun showError(baseResponse: BaseResponseModel) {
         eventError.value = Event(baseResponse)
     }

    //-----


    fun showFailure(error: Throwable) {
        if(error is SocketTimeoutException){

            //showErrorMessage(-1, error.message.toString())
            showErrorMessage(-1,"Request timeout")
        }else if(error is ConnectException || error is UnknownHostException){
           // showErrorMessage(error.hashCode(), error.message.toString())
            showErrorMessage(error.hashCode(), "Error connect internet!")
        }else if(error is HttpException){
            when (error.code()) {
                HttpsURLConnection.HTTP_UNAUTHORIZED -> showErrorMessage(error.code(),"Unauthorised User ")
                HttpsURLConnection.HTTP_FORBIDDEN ->showErrorMessage(error.code(),"Forbidden")
                HttpsURLConnection.HTTP_INTERNAL_ERROR -> showErrorMessage(error.code(),"Internal Server Error")
                HttpsURLConnection.HTTP_BAD_REQUEST -> showErrorMessage(error.code(),"Bad Request")
                else -> showErrorMessage(error.code(), error.message.toString())
            }
        }else if(error is JsonSyntaxException){
            showErrorMessage(-1, "Something Went Wrong API is not responding properly!")
        }else{
            showErrorMessage(-1, error.message.toString())
        }

        isLoading(false)
    }
}
fun showErrorMessage(code:Int,message:String){
//    val activity = BaseApplication.activeActivity
//    if(activity!=null){
//        if(activity is BaseActivity){
//            activity.showBaseMessage("Message!",message)
//        }
//    }
}
fun isLoading(isShow:Boolean){
//    val activity = BaseApplication.activeActivity
//    if(activity!=null){
//        if(activity is BaseActivity){
//            activity.showLoading(isShow)
//        }
//    }
}