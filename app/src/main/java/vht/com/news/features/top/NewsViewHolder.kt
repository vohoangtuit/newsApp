package vht.com.news.features.top

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import kotlinx.android.synthetic.main.item_news.view.*
import vht.com.news.components.recyclerview.viewHolder.ItemViewHolder
import vht.com.news.models.Article

class NewsViewHolder(itemView: View) : ItemViewHolder<Article>(itemView) {
    var title : TextView= itemView.title
    var desc : TextView= itemView.desc
    var author : TextView= itemView.author
    var published_ad : TextView= itemView.publishedAt
    var source : TextView= itemView.source
    var time : TextView= itemView.time
    var imageView : ImageView= itemView.img

}