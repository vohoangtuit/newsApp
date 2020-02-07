package vht.com.news.components.recyclerview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import vht.com.news.R
import vht.com.news.components.recyclerview.OnItemClickListener
import vht.com.news.components.recyclerview.viewHolder.ItemViewHolder
import vht.com.news.components.recyclerview.viewHolder.LoadMoreViewHolder
import vht.com.news.utils.EnumGeneric


abstract class BaseAdapter<T>(private val inflater: LayoutInflater,
                              val items: ArrayList<T>,
                              var itemClickInterface: OnItemClickListener<T>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), View.OnClickListener {
    var ITEM_TYPE =1
    var FOOTER_TYPE=2
    var SECTION_HEADER_TYPE=3

    // 1. ITEM
    protected abstract fun itemViewHolderClass(): Class<out ItemViewHolder<*>>
    protected abstract fun itemLayoutResource(): Int

    // 2 FOOTER
    var footerViewHolderClass:Class<out FooterViewHolder<*>>?=null
    fun setFooterViewHolder(viewHolder: Class<out FooterViewHolder<*>>){
        footerViewHolderClass =viewHolder
    }

    fun getFooterViewHolder():Class<out FooterViewHolder<*>>{
        return this!!.footerViewHolderClass!!
    }
   var footerLayoutResource:Int=0
    fun setFooterLayout(layout:Int){
        footerLayoutResource =layout
    }
    fun getFooterLayout(): Int{
        return footerLayoutResource
    }

    //3 SENSOR HEADER
    //
    var canLoadMore: Boolean =false
    fun setLoadMore(canLoadMore: Boolean){
        this.canLoadMore =canLoadMore
    }
    fun getLoadMore(): Boolean{
        return this.canLoadMore
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view: RecyclerView.ViewHolder? = null
        if(viewType==FOOTER_TYPE){
            val footerView = getFooterViewHolder().getConstructor(View::class.java)
            view =footerView.newInstance(inflater.inflate(getFooterLayout(),parent,false))
        }

        else{
            val itemView = itemViewHolderClass().getConstructor(View::class.java)
            view = itemView.newInstance(inflater.inflate( itemLayoutResource(), parent, false)) as RecyclerView.ViewHolder
        }

        if (view?.itemView != null) {
            if (!view.isRecyclable)
                view.setIsRecyclable(true)
            view.itemView.setOnClickListener(this)
            view.itemView.tag = view
        }
        return view!!
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder.itemViewType==FOOTER_TYPE){
            val item = holder as FooterViewHolder<*>
            bindFooterView(item as FooterViewHolder<T>, items[position], position)
            item.data = items[position]
        }
        else{
            val item = holder as ItemViewHolder<*>
            bindItemView(item as ItemViewHolder<T>, items[position], position)
            item.data = items[position]
        }
    }
    override fun getItemCount(): Int {
        return items.size
    }
    protected open fun bindItemView(holder: ItemViewHolder<T>, data: T, position: Int) {
    }
    protected open fun bindFooterView(holder: FooterViewHolder<T>?, data: T, position: Int) { // override if there is footer
    }

    override fun onClick(v: View?) {
        val holder = v?.tag
        if (holder is ItemViewHolder<*>) {
            itemClickInterface?.onItemClick(holder.data as T, holder.adapterPosition, EnumGeneric.ClickType.Default)
        }
    }
    fun viewType(position: Int):Int{
        if(items==null||items.size==0){
            return ITEM_TYPE
        }
        return ITEM_TYPE
    }
    fun setViewLoadMore(){
        setFooterLayout(R.layout.view_progressbar)
        setFooterViewHolder(LoadMoreViewHolder::class.java)
    }

    fun checkListLoadMore(position: Int):Int{
        if(position==items.size-1 && getLoadMore()){
            setViewLoadMore()
            return FOOTER_TYPE
        }
        else return ITEM_TYPE
    }
}