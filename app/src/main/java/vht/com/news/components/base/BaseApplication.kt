package vht.com.news.base

import android.annotation.SuppressLint
import android.content.Context
import androidx.multidex.MultiDexApplication
import androidx.appcompat.app.AppCompatActivity

@SuppressLint("Registered")
 abstract class BaseApplication : MultiDexApplication()  {
    override fun onCreate() {
        super.onCreate()
        instance = this
        context = applicationContext
    }
    companion object {
        val TAG = BaseApplication::class.java
                .simpleName
        @SuppressLint("StaticFieldLeak")
        @get:Synchronized
        var instance: BaseApplication? = null
            private set
        @SuppressLint("StaticFieldLeak")
        var context: Context? = null
            private set
        var activeActivity: AppCompatActivity? = null

    }

}