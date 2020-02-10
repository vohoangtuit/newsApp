package vht.com.news.components.application

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ProcessLifecycleOwner
import com.facebook.stetho.Stetho
import com.uphyca.stetho_realm.RealmInspectorModulesProvider
import io.realm.BuildConfig
import io.realm.Realm
import io.realm.RealmConfiguration
import vht.com.news.base.BaseApplication

class NewsApplication : BaseApplication(), LifecycleObserver {
    override fun onCreate() {
        super.onCreate()
        instance = this
        context = applicationContext
        Realm.init(this)
        setupLifecycleListener()
        initRealmData()
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
     fun setupLifecycleListener() {
        ProcessLifecycleOwner.get().lifecycle
            .addObserver(this)
    }
    fun initRealmData(){
        val config = RealmConfiguration.Builder()
            .name("android_db.realm")
            .schemaVersion(1)
            //.migration(DBMigration())
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.setDefaultConfiguration(config)

        if(BuildConfig.DEBUG){
            // chrome://inspect/#devices
            Stetho.initialize( Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                .build())
        }
    }
}