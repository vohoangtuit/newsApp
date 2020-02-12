package vht.com.news.components.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import vht.com.news.base.BaseApplication
import vht.com.news.features.NewsMainActivity
import vht.com.news.utils.KeyboardManager
import vht.com.news.viewModel.BaseViewModel

abstract class BaseFragment : Fragment(),BaseInterface{
    private var finishedResultCode = -1
    private var finishedResult: Intent? = null
    private var requestCode: Int = 0
    private var isFinishedWithResult = false
    val RESULT_OK = 1
    var activeActivity: BaseActivity? = null
    override fun getActiveActivity(): AppCompatActivity {
        return BaseApplication.activeActivity!!
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBindView()
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getRootLayoutId(), container, false)
    }
    override fun onResume() {
        super.onResume()
        onBaseResume()
    }

    abstract fun getRootLayoutId(): Int
    override fun getEnterAnimation(): Int {
        return 0
    }


    //---------
    open fun onPostResumeWithResult(requestCode: Int, finishedResultCode: Int, finishedResult: Intent) {

    }

    @IdRes
    fun getMainContainerId(): Int {
        return if (activity != null && activity is BaseActivity)
            (activity as BaseActivity)
                .getFragmentContainerResId()
        /* else if (getActiveActivity() is BaseActivity)
             (getActiveActivity() as BaseActivity)
                 .getFragmentContainerResId()*/
        else
            activeActivity?.getFragmentContainerResId() ?: 0
    }

    fun addFragment(fragment: BaseFragment) {
        if (activity != null && activity is BaseActivity)
            (activity as BaseActivity).addFragment(getMainContainerId(), fragment)
        else if (getActiveActivity() is BaseActivity)
            (getActiveActivity() as BaseActivity).addFragment(getMainContainerId(), fragment)
        else
            activeActivity?.addFragment(getMainContainerId(), fragment)
    }

    fun replaceFragment(fragment: BaseFragment, clearStack: Boolean) {
        if (activity != null && activity is BaseActivity)
            (activity as BaseActivity).replaceFragment(getMainContainerId(), fragment, clearStack)
        else
            activeActivity?.replaceFragment(getMainContainerId(), fragment, clearStack)
    }
    fun addFragmentForResult(requestCode: Int, toFragment: BaseFragment) {
        if (activity != null && activity is BaseActivity)
            (activity as BaseActivity).addFragmentForResult(
                getMainContainerId(),
                requestCode,
                toFragment
            )
        else if (getActiveActivity() is BaseActivity)
            (getActiveActivity() as BaseActivity).addFragmentForResult(
                getMainContainerId(),
                requestCode,
                toFragment
            )
        else
            activeActivity?.addFragmentForResult(getMainContainerId(), requestCode, toFragment)
    }
    fun popFragment() {
        if (activity != null && activity is BaseActivity)
            (activity as BaseActivity).popFragment(getMainContainerId())
        else if (getActiveActivity() != null && getActiveActivity() is BaseActivity)
            (getActiveActivity() as BaseActivity).popFragment(getMainContainerId())
        else
            activeActivity?.popFragment(getMainContainerId())
    }
    fun getTopFragment(): BaseFragment? {
        return if (activity != null && activity is BaseActivity)
            (activity as BaseActivity).getTopFragment(getMainContainerId())
        else if (getActiveActivity() != null && getActiveActivity() is BaseActivity)
            (getActiveActivity() as BaseActivity).getTopFragment(getMainContainerId())
        else
            activeActivity?.getTopFragment(getMainContainerId())
    }
    fun onBackPressed() {

        if (activity != null && activity is BaseActivity) {
            if (KeyboardManager.isShowSoftKeyboard(activity as BaseActivity)) {
                KeyboardManager.hideSoftKeyboard(activity as BaseActivity)
            } else {
                (activity as BaseActivity).onBackPressed()
            }
        } else {
            if (KeyboardManager.isShowSoftKeyboard(activeActivity!!)) {
                KeyboardManager.hideSoftKeyboard(activeActivity!!)
            } else {
                activeActivity?.onBackPressed()
            }

        }
    }

    fun finishWithResult(resultCode: Int, result: Intent) {
        isFinishedWithResult = true
        finishedResultCode = resultCode
        finishedResult = result
        popFragment()
    }

    fun getFinishedResultCode(): Int {
        return finishedResultCode
    }

    fun setFinishedResultCode(finishedResultCode: Int) {
        this.finishedResultCode = finishedResultCode
    }

    fun getFinishedResult(): Intent? {
        return finishedResult
    }

    fun setFinishedResult(finishedResult: Intent) {
        this.finishedResult = finishedResult
    }

    fun getRequestCode(): Int {
        return requestCode
    }

    fun setRequestCode(requestCode: Int) {
        this.requestCode = requestCode
    }
    fun isFinishedWithResult(): Boolean {
        return isFinishedWithResult
    }

    fun setFinishedWithResult(finishedWithResult: Boolean) {
        isFinishedWithResult = finishedWithResult
    }

}
