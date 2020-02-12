package vht.com.news.features.preference

import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.item_catalogue.view.*
import vht.com.news.components.recyclerview.viewHolder.ItemViewHolder
import vht.com.news.models.CatalogueModel

class CataloguaViewHolder(itemView: View) : ItemViewHolder<CatalogueModel>(itemView) {
    var tvName: TextView = itemView.item_catalogue_tv_name
}