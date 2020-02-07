package vht.com.news.components

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.ProgressBar
import vht.com.news.R

class Loading(context: Context) : Dialog(context, R.style.dialog_full_transparent_background) {
    init {
        val windowArr = window!!.attributes
        windowArr.gravity = Gravity.CENTER_HORIZONTAL
        window!!.attributes = windowArr
        setTitle(null)
        setCancelable(false)
        setOnCancelListener(null)
        val layout = LinearLayout(context)
        layout.orientation = LinearLayout.VERTICAL
        val params = LinearLayout.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        layout.addView(ProgressBar(context), params)
        addContentView(layout, params)
    }
}
