package vht.com.news.components.application

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
        Realm.init(this)
        setupLifecycleListener()
        initRealmData()
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