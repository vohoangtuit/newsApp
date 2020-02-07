package vht.com.news.components.base

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import vht.com.news.base.BaseApplication
import vht.com.news.components.Loading
import vht.com.news.viewModel.BaseViewModel

 abstract class BaseActivity : AppCompatActivity(),BaseInterface{
     private var loading: Loading? = null
     override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         setContentView(getRootLayoutId())
         BaseApplication.activeActivity = this
         onBindView()
     }

     override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
         super.onCreate(savedInstanceState, persistentState)
         BaseApplication.activeActivity = this
     }

     override fun onResume() {
         super.onResume()
         BaseApplication.activeActivity = this
     }

     override fun getActiveActivity(): AppCompatActivity {
         return BaseApplication.activeActivity!!
     }


     override fun setContentView(view: View, params: ViewGroup.LayoutParams) {
         super.setContentView(view, params)
          //onBindView()
     }
     abstract fun getRootLayoutId(): Int

     override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm?.hideSoftInputFromWindow(v.getWindowToken(), 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }
    fun setObserveLive(viewModel: BaseViewModel) {
        viewModel.eventLoading.observe(this, Observer {
            if (it != null) {
                if (it.getContentIfNotHandled() != null) {
                    if (it.peekContent()) {
                        showLoading(true)
                    } else {
                        showLoading(false)
                    }
                }
            }
        })
        viewModel.eventFailure.observe(this, Observer {
            if (it != null) {
                if (it.getContentIfNotHandled() != null) {
                    //showMessageFailRequest(it.peekContent())
                }
            }
        })
    }
    fun showLoading(isShow: Boolean) {
        if (isShow) {
            if (loading == null)
                loading = Loading(getActiveActivity())
            loading!!.show()
        } else {
            if (loading != null) {
                loading!!.dismiss()
            }
        }
    }
}