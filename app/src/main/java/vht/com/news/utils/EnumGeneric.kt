package vht.com.news.utils

class EnumGeneric {
    enum class ClickType {
        Default,
        Edit
    }

    enum class AdapterViewHolder (val value: Int) {
        LOAD_MORE_HOLDER(0),
        USER_HOLDER(1),
        FILE_HOLDER(2)

    }
}
