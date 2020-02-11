package vht.com.news.features.detailNews

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.krishna.fileloader.utility.Utils
import kotlinx.android.synthetic.main.fragment_detail_news.*
import vht.com.news.R
import vht.com.news.features.general.fragment.GeneralFragment
import vht.com.news.models.Article
import vht.com.news.utils.TimeFormat
import vht.com.news.utils.Utils.glideRequestOptions

class NewsDetailScreen: GeneralFragment() {
    companion object{
        private const val USER_MODEL = "UserModel"
        fun newInstance(article: Article): NewsDetailScreen {
            val fragment = NewsDetailScreen()
            val args = Bundle()
            args.putSerializable(USER_MODEL, article)
            fragment.arguments = args
            return fragment
        }
        lateinit var article : Article
    }
    override fun getRootLayoutId(): Int {
        return R.layout.fragment_detail_news
    }

    override fun onBindView() {
        initTolbar()
        if (arguments != null) {
            val article = arguments!!.getSerializable(USER_MODEL) as Article
            handleData(article)
        }
    }

    override fun onBaseResume() {
       showToolbar(false)
    }
    fun initTolbar(){
        getActiveActivity().setSupportActionBar(toolbar)
        getActiveActivity().supportActionBar!!.title = ""
        getActiveActivity().getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        collapsing_toolbar.setTitle("")
    }
    fun handleData(article: Article){
        Glide.with(this)
            .load(article.urlToImage)
            .apply(glideRequestOptions())
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(backdrop)

        title_on_appbar.text= article.source.toString()
        subtitle_on_appbar.text =article.url
        date.text = TimeFormat.DateFormat(article.publishedAt)

        title.text =article.title

        val author: String
        author = if (article.author != null) {
            " \u2022 ${article.author}"
        } else {
            ""
        }

       // time.setText(article.source + author + " \u2022 " + Utils.DateToTimeFormat(mDate))

        initWebView(article.urlToImage.toString())
    }

    private fun initWebView(url: String) {
        webView.settings.loadsImagesAutomatically = true
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.setSupportZoom(true)
        webView.settings.builtInZoomControls = true
        webView.settings.displayZoomControls = false
        webView.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        webView.webViewClient = WebViewClient()
        webView.loadUrl(url)


    }
}