package vht.com.news.components.recyclerview

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import vht.com.news.components.recyclerview.adapter.BaseAdapter


class CVRecyclerView : RecyclerView {
     var canLoadMore: Boolean = false
    private var adapter: BaseAdapter<*>? = null
    private var onLoadMoreListener: OnLoadMoreListener? = null

    constructor(context: Context) : super(context) {init()}
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {init()}
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle) {
        init()
    }
    fun setLoadMore(loadMore: Boolean){
       canLoadMore =loadMore
        adapter?.setLoadMore(loadMore)
       // adapter?.canLoadMore =loadMore
    }
    fun getLoadMore(): Boolean{
        return canLoadMore
    }
    fun setHorizontal() {
        this.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            true
        )
    }
    override fun setAdapter(adapter: Adapter<*>?) {
        super.setAdapter(adapter)
        if (adapter is BaseAdapter<*>)
            this.adapter = adapter
    }
    interface OnLoadMoreListener {

        fun onLoadMore()

        fun shouldOverrideRefresh(): Boolean
    }


    fun setOnLoadMoreListener(onLoadMoreListener: OnLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener
    }
    fun onLoadMore() {
        if (onLoadMoreListener != null) {
            onLoadMoreListener!!.onLoadMore()
        }
    }
    fun onLoadMoreComplete() {
        if (adapter != null)
            adapter!!.notifyDataSetChanged()
        //showLoadProgressBar(false)
    }
    fun setGridLayout(column: Int, spacing: Int){
        this.layoutManager = GridLayoutManager(context,column)
        this.addItemDecoration(
            GridItemDecoration(
                spacing,
                2
            )
        )
    }
    private fun init() {
        setHasFixedSize(true)
//        this.layoutManager = LinearLayoutManager(
//            context,
//            LinearLayoutManager.VERTICAL,
//            true
//        )
        //createProgressView()
    }

    override fun onScrolled(dx: Int, dy: Int) {
        super.onScrolled(dx, dy)
        val linearLayoutManager = this.getLayoutManager() as LinearLayoutManager
        if(adapter != null && linearLayoutManager != null){
            val totalItemCount = adapter!!.getItemCount()
            if (canLoadMore) {
                if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == totalItemCount - 1) {
                    onLoadMoreListener?.onLoadMore()
                  //  showLoadProgressBar(true)
                }
            }
        }
    }
    override fun onScreenStateChanged(screenState: Int) {
        super.onScreenStateChanged(screenState)
    }
    fun setEnablePagingProgress(enable: Boolean) {
    }
    class GridItemDecoration(gridSpacingPx: Int, gridSize: Int) : RecyclerView.ItemDecoration() {
        private var mSizeGridSpacingPx: Int = gridSpacingPx
        private var mGridSize: Int = gridSize

        private var mNeedLeftSpacing = false

        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            val frameWidth =
                ((parent.width - mSizeGridSpacingPx.toFloat() * (mGridSize - 1)) / mGridSize).toInt()
            val padding = parent.width / mGridSize - frameWidth
            val itemPosition =
                (view.getLayoutParams() as RecyclerView.LayoutParams).viewAdapterPosition
            if (itemPosition < mGridSize) {
                outRect.top = 0
            } else {
                outRect.top = mSizeGridSpacingPx
            }
            if (itemPosition % mGridSize == 0) {
                outRect.left = 0
                outRect.right = padding
                mNeedLeftSpacing = true
            } else if ((itemPosition + 1) % mGridSize == 0) {
                mNeedLeftSpacing = false
                outRect.right = 0
                outRect.left = padding
            } else if (mNeedLeftSpacing) {
                mNeedLeftSpacing = false
                outRect.left = mSizeGridSpacingPx - padding
                if ((itemPosition + 2) % mGridSize == 0) {
                    outRect.right = mSizeGridSpacingPx - padding
                } else {
                    outRect.right = mSizeGridSpacingPx / 2
                }
            } else if ((itemPosition + 2) % mGridSize == 0) {
                mNeedLeftSpacing = false
                outRect.left = mSizeGridSpacingPx / 2
                outRect.right = mSizeGridSpacingPx - padding
            } else {
                mNeedLeftSpacing = false
                outRect.left = mSizeGridSpacingPx / 2
                outRect.right = mSizeGridSpacingPx / 2
            }
            outRect.bottom = 0
        }
    }
}