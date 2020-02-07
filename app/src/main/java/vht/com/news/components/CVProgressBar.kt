package vht.com.news.components

import android.content.Context
import android.util.AttributeSet
import android.widget.ProgressBar

class CVProgressBar: ProgressBar {
    constructor(context: Context) : super(context){
        init()
    }

    constructor(context: Context, attrs: AttributeSet):    super(context, attrs){
        init()
    }
    fun init(){

    }
    fun setCount(){

    }
    fun showProgress(){

    }
    fun dismiss(){
        this.dismiss()
    }

}