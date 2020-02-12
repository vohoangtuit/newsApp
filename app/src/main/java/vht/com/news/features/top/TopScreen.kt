package vht.com.news.features.top

import android.view.LayoutInflater
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.fragment_top.*
import vht.com.news.R
import vht.com.news.components.recyclerview.OnItemClickListener
import vht.com.news.features.detailNews.DialogDetail
import vht.com.news.features.detailNews.NewsDetailScreen
import vht.com.news.features.general.fragment.GeneralFragment
import vht.com.news.models.Article
import vht.com.news.utils.EnumGeneric
import vht.com.news.viewModel.NewsViewModel

class TopScreen: GeneralFragment(), SwipeRefreshLayout.OnRefreshListener {
    lateinit   var adapter: NewsAdapter
    lateinit  var newsViewModel: NewsViewModel
    var dataResponse :ArrayList<Article> = ArrayList()
    companion object {
        fun newInstance(): TopScreen {
            return TopScreen()
        }

    }


    override fun getRootLayoutId(): Int {
        return R.layout.fragment_top
    }

    override fun onBindView() {
        swipe_refresh_layout.setOnRefreshListener(this)
        bindingViewModel()
        setUpRecyclerview()
    }

    override fun onBaseResume() {

    }
    fun setUpRecyclerview() {
        adapter = NewsAdapter(LayoutInflater.from(getActiveActivity()), dataResponse, object :
            OnItemClickListener<Article> {
                override fun onItemClick(item: Article, position: Int, typeClick: EnumGeneric.ClickType) {
                    DialogDetail(getActiveActivity(),item).show()
                }
            })

        recyclerView.adapter = adapter

    }
    fun bindingViewModel() {

            newsViewModel = ViewModelProviders.of(this).get(NewsViewModel::class.java)
            setObserveLive(newsViewModel)
            newsViewModel.newsResponse().observe(this, Observer {
                if(it!=null){
                    if(it.article!=null){
                        if (!dataResponse.isEmpty()) {
                            dataResponse.clear()
                        }
                        dataResponse.addAll(it.article)
                        recyclerView.onLoadMoreComplete()

                    }

                }
            })

        getData()
    }
    fun getData(){
        newsViewModel.getListNews(swipe_refresh_layout)
    }

    override fun onRefresh() {
        getData()
    }
}