package vht.com.news.features.preference

import android.annotation.SuppressLint
import android.view.LayoutInflater
import vht.com.news.R
import vht.com.news.components.recyclerview.OnItemClickListener
import vht.com.news.components.recyclerview.adapter.BaseAdapter
import vht.com.news.components.recyclerview.viewHolder.ItemViewHolder
import vht.com.news.features.preference.CataloguaViewHolder
import vht.com.news.models.CatalogueModel

open class CatalogueAdapter (inflater: LayoutInflater, items: ArrayList<CatalogueModel>, itemClickInterface: OnItemClickListener<CatalogueModel>) : BaseAdapter<CatalogueModel>(inflater, items, itemClickInterface){
    override fun itemViewHolderClass(): Class<out ItemViewHolder<*>> {
        return CataloguaViewHolder::class.java
    }

    override fun itemLayoutResource(): Int {
        return R.layout.item_catalogue
    }
    @SuppressLint("ResourceAsColor")
    override fun bindItemView(holder: ItemViewHolder<CatalogueModel>, data: CatalogueModel, position: Int) {
        if (holder is CataloguaViewHolder) {
            holder.tvName.text =data.name
            if(data.selected){
                holder.tvName.setBackgroundResource(R.drawable.bg_primary_solid_no_border_30_radius)
                holder.tvName.isSelected = true
            }else{
                holder.tvName.setBackgroundResource(R.drawable.bg_white_border_primary_30_radius)
                holder.tvName.isSelected = false
            }
        }
    }
    open fun updateSelected(item: CatalogueModel){
        for(catalogue in items){
            if(catalogue.selected)
                catalogue.selected =false
        }
        item.selected =true
        notifyDataSetChanged()
    }
}