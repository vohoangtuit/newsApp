package vht.com.news

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*
import vht.com.news.components.base.BaseActivity
import vht.com.news.viewModel.NewsViewModel

class MainActivity : BaseActivity() {
    private lateinit var newsViewModel: NewsViewModel

    override fun getRootLayoutId(): Int {
        return  R.layout.activity_main
    }

    override fun onBindView() {
        initViewModel()
    }

    override fun onBaseResume() {

    }

    fun initViewModel(){
        newsViewModel = ViewModelProviders.of(this).get(NewsViewModel::class.java)
        setObserveLive(newsViewModel)
        newsViewModel.newsResponse().observe(this, Observer {
            if(it!=null){
                tv_content.setText(it.toString())

            }

        })
        newsViewModel.getListNews()
    }

    override fun getEnterAnimation(): Int {
        return 0
    }

}
