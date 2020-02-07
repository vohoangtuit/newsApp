package vht.com.news.components.recyclerview.viewHolder

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class ItemViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var data: T? = null
    fun findViewById(id: Int): View {
        return itemView.findViewById(id)
    }
    fun setEnable(b: Boolean) {
        itemView.isEnabled = b
    }
}