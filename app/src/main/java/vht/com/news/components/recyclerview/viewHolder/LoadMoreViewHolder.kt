package vht.com.news.components.recyclerview.viewHolder
import android.view.View

import android.widget.ProgressBar


import kotlinx.android.synthetic.main.view_progressbar.view.*
import vht.com.news.components.recyclerview.adapter.FooterViewHolder


class LoadMoreViewHolder (itemView: View) : FooterViewHolder<Any>(itemView) {
    var progressbar: ProgressBar = itemView.progressbar
}