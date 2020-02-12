package vht.com.news.features.preference

import android.view.LayoutInflater
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_preference.*
import vht.com.news.R
import vht.com.news.components.recyclerview.OnItemClickListener
import vht.com.news.features.detailNews.DialogDetail
import vht.com.news.features.detailNews.NewsDetailScreen
import vht.com.news.features.general.fragment.GeneralFragment
import vht.com.news.features.top.NewsAdapter
import vht.com.news.models.Article
import vht.com.news.models.CatalogueModel
import vht.com.news.utils.EnumGeneric
import vht.com.news.viewModel.NewsViewModel

class PreferenceScreen: GeneralFragment() {
    lateinit   var adapterSearch: NewsAdapter
    lateinit  var newsViewModelSearch: NewsViewModel
    var dataResponseSearch :ArrayList<Article> = ArrayList()
    lateinit var  adapterCatalogueSearch : CatalogueAdapter
    companion object {

    }
    override fun getRootLayoutId(): Int {
       return R.layout.fragment_preference
    }

    override fun onBindView() {
        //setUpRecyclerview()
        bindingViewModel()
        initCatalogue()

    }

    override fun onBaseResume() {

    }
    fun bindingViewModel() {
        newsViewModelSearch = ViewModelProviders.of(this).get(NewsViewModel::class.java)
        setObserveLive(newsViewModelSearch)
        newsViewModelSearch.searchResponse().observe(this, Observer {
            if(it!=null){
                if(it.article!=null){
                    if(!dataResponseSearch.isEmpty()){
                        dataResponseSearch.clear()
                    }
                    dataResponseSearch.addAll(it.article)
                    adapterSearch = NewsAdapter(LayoutInflater.from(getActiveActivity()),
                        dataResponseSearch, object :
                            OnItemClickListener<Article> {
                            override fun onItemClick(item: Article, position: Int, typeClick: EnumGeneric.ClickType) {
                                //addFragment(NewsDetailScreen.newInstance(item))
                                DialogDetail(getActiveActivity(),item).show()
                            }
                        })

                    recycler_search.adapter = adapterSearch
                    recycler_search.onLoadMoreComplete()

                }

            }
        })

    }
    fun initCatalogue(){
        var listData :ArrayList<CatalogueModel> = ArrayList()
        listData.add(CatalogueModel("1","Bitcoin",true));
        listData.add(CatalogueModel("2","Apple",false));
        listData.add(CatalogueModel("3","Earthquake",false));
        listData.add(CatalogueModel("4","Animal",false));

        adapterCatalogueSearch =CatalogueAdapter(
           LayoutInflater.from(getActiveActivity()), listData, object :
                OnItemClickListener<CatalogueModel> {
                override fun onItemClick(
                    item: CatalogueModel,
                    position: Int,
                    typeClick: EnumGeneric.ClickType
                ) {
                    rcv_catalogue.scrollToPosition(position)
                    item.selected = true
                    adapterCatalogueSearch.updateSelected(item)

                    searchByCatalogue(item)
                }

            })

        rcv_catalogue.adapter =adapterCatalogueSearch
        searchByCatalogue(listData[0])
    }
    fun setUpRecyclerview() {
        adapterSearch = NewsAdapter(LayoutInflater.from(getActiveActivity()),
            dataResponseSearch, object :
                OnItemClickListener<Article> {
                override fun onItemClick(item: Article, position: Int, typeClick: EnumGeneric.ClickType) {
                    //addFragment(NewsDetailScreen.newInstance(item))
                    DialogDetail(getActiveActivity(),item).show()
                }
            })

        recycler_search.adapter = adapterSearch

    }
    fun searchByCatalogue(catalogue: CatalogueModel){
      newsViewModelSearch.searchNews(catalogue.name!!)

    }

}