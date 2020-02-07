package vht.com.news.components.recyclerview

import vht.com.news.utils.EnumGeneric


interface OnItemClickListener<T> {

    fun onItemClick(item: T, position: Int, typeClick: EnumGeneric.ClickType)
}