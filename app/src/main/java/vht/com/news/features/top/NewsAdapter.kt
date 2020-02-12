package vht.com.news.features.top

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.Nullable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import vht.com.news.utils.Utils
import vht.com.news.R
import vht.com.news.base.BaseApplication.Companion.activeActivity
import vht.com.news.components.application.NewsApplication
import vht.com.news.components.recyclerview.OnItemClickListener
import vht.com.news.components.recyclerview.adapter.BaseAdapter
import vht.com.news.components.recyclerview.viewHolder.ItemViewHolder
import vht.com.news.models.Article
import vht.com.news.utils.TimeFormat

class NewsAdapter(inflater: LayoutInflater, items: ArrayList<Article>, itemClickInterface: OnItemClickListener<Article>) : BaseAdapter<Article>(inflater, items, itemClickInterface) {
    override fun itemViewHolderClass(): Class<out ItemViewHolder<*>> {
        return NewsViewHolder::class.java
    }

    override fun itemLayoutResource(): Int {
       return R.layout.item_news
    }

    override fun bindItemView(holder: ItemViewHolder<Article>, data: Article, position: Int) {
        //super.bindItemView(holder, data, position)

        if(holder is NewsViewHolder){

            holder.title.text =data.title
            holder.desc.text =data.description
            holder.source.text =data.source?.name
            holder.author.text =data.author
            holder.time.text =" \u2022 " + TimeFormat.DateToTimeFormat(data.publishedAt)
            holder.published_ad.text=TimeFormat.DateFormat(data.publishedAt)

            Glide.with(activeActivity!!).asBitmap().load(data.urlToImage).into(
                    holder.imageView
                )


        }

    }
}