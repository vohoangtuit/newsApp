package vht.com.news.features.general.activity

import androidx.lifecycle.Observer
import vht.com.news.R
import vht.com.news.components.Loading
import vht.com.news.components.base.BaseActivity
import vht.com.news.components.base.BaseFragment
import vht.com.news.viewModel.BaseViewModel

abstract class GeneralActivity: BaseActivity(){
    private var loading: Loading? = null
    override fun getFragmentContainerResId(): Int {
        return R.id.activity_main_frame_layout
    }
    fun addFragment(fragment: BaseFragment) {
        addFragment(getFragmentContainerResId(), fragment)
    }


    fun replaceFragment(fragment: BaseFragment, clearStack: Boolean) {
        replaceFragment(getFragmentContainerResId(), fragment, clearStack)
    }

    fun getTopFragment(): BaseFragment? {
        return getTopFragment(getFragmentContainerResId())
    }
    override fun onResume() {
        super.onResume()
        onBaseResume()
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