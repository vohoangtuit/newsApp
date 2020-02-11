package vht.com.news.features.general.fragment

import android.os.Bundle
import android.view.View
import vht.com.news.components.base.BaseFragment
import vht.com.news.features.NewsMainActivity
import vht.com.news.viewModel.BaseViewModel

abstract class GeneralFragment: BaseFragment(){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBindView()
    }
    override fun onResume() {
        super.onResume()
        showToolbar(true)
        onBaseResume()
    }
    fun activity(): NewsMainActivity{
        return  (getActiveActivity() as NewsMainActivity)
    }
    fun setObserveLive(viewModel: BaseViewModel){
        activity().setObserveLive(viewModel)
    }
    fun showToolbar(show: Boolean){
        activity().showToolbar(show)
    }
    fun showBackToolbar(show: Boolean){
        activity().showBackToolbar(show)
    }
    fun updateTitle(title: String) {
        activity().updateTitle(title)
    }
    fun showLoading(show: Boolean) {
        activity().showLoading(show)
    }
}