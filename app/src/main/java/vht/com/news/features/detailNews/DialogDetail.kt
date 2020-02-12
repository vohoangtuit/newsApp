package vht.com.news.features.detailNews

import android.provider.MediaStore
import android.view.View
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.base_message_dialog.*
import kotlinx.android.synthetic.main.fragment_detail_news.*
import vht.com.news.R
import vht.com.news.features.general.dialog.GeneralDialog
import vht.com.news.models.Article
import vht.com.news.utils.TimeFormat
import vht.com.news.utils.Utils

class DialogDetail (context: AppCompatActivity, article: Article): GeneralDialog(context), View.OnClickListener,
    AppBarLayout.OnOffsetChangedListener{
    var isHideToolbarView = false

    var mContext =context
    var mArticle =article
    override fun getRootLayoutId(): Int {
        return R.layout.fragment_detail_news    }

    override fun initContentView() {
       setFullScreen()
        initTolbar()
        handleData(mArticle)
    }

    override fun onClick(v: View?) {

    }

    fun initTolbar(){
        mContext.setSupportActionBar(toolbar)
        mContext.supportActionBar!!.title = ""
        mContext.getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        collapsing_toolbar.setTitle("")
        toolbar.setNavigationOnClickListener(View.OnClickListener {
            dismiss()
        })
    }
    fun handleData(article: Article){
        Glide.with(mContext)
            .load(article.urlToImage)
            .apply(Utils.glideRequestOptions())
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(backdrop)

        date.text = TimeFormat.DateFormat(article.publishedAt)

        titleNew.text =article.title

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

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        val maxScroll = appBarLayout!!.totalScrollRange
        val percentage = Math.abs(verticalOffset).toFloat() / maxScroll.toFloat()

        if (percentage == 1f && isHideToolbarView) {
            date_behavior.visibility = View.GONE
            isHideToolbarView = !isHideToolbarView
        } else if (percentage < 1f && !isHideToolbarView) {
            date_behavior.visibility = View.VISIBLE

            isHideToolbarView = !isHideToolbarView
        }
    }


}