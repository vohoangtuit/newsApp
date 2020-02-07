package vht.com.news.components.recyclerview.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

open class FooterViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var data: T? = null

    init {
    }

    fun findViewById(id: Int): View {
        return itemView.findViewById(id)
    }

    fun setEnable(b: Boolean) {
        itemView.isEnabled = b
    }
}