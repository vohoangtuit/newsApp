package vht.com.news.features.general.fragment

import android.graphics.Rect
import android.os.Bundle
import android.os.Handler
import android.view.View
import vht.com.news.components.base.BaseFragment
import vht.com.news.features.NewsMainActivity
import vht.com.news.features.main.MainScreen
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
    fun showMenuButton(show: Boolean){
        activity().showToolbar(show)
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

    fun checkKeyBordShowing(activityRootView: View){
        view!!.viewTreeObserver.addOnGlobalLayoutListener {
            val r = Rect()

            view!!.getWindowVisibleDisplayFrame(r)
            val heightDiff: Int = view!!.rootView.height - (r.bottom - r.top)
            val fragment = getTopFragment()
            if(fragment!=null){
                if(fragment is MainScreen){
                    if (heightDiff > 300) {
                        fragment.showTabMenu(false)

                    }else{
                        Handler().postDelayed({
                            fragment.showTabMenu(true)
                        }, 200L)
                    }
                }
            }

        }

    }

}