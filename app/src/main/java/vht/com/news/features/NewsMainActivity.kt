package vht.com.news.features

import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.view_base_toolbar.*
import vht.com.news.R
import vht.com.news.features.general.activity.GeneralActivity
import vht.com.news.features.main.MainScreen

class NewsMainActivity : GeneralActivity(), View.OnClickListener {

    override fun getRootLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun onBindView() {
        toolbar_view_back.setOnClickListener(this)
        addFragment(MainScreen())
    }

    override fun onBaseResume() {
        showToolbar(true)
    }


    fun showToolbar(show: Boolean) {
        view_toolbar.isVisible = show
    }
    fun showBackToolbar(show: Boolean) {
        toolbar_view_back.isVisible = show
    }
    fun updateTitle(title: String) {
        toolbar_tv_title.text =title
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.toolbar_view_back -> {
                handleBackPressed()
            }

        }
    }
    override fun onBackPressed() {
        handleBackPressed()
    }


    fun handleBackPressed(){
        val fragment = getTopFragment()
        if(fragment!=null){
            if (getBackStackCount() > 0) {
                backStack()
            } else {
                onBackPressed()
            }
        }
    }
}
