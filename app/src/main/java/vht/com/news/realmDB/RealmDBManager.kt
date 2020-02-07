package vht.com.news.realmDB

import io.realm.ImportFlag
import io.realm.Realm
import io.realm.RealmModel
import io.realm.RealmObject
import io.realm.exceptions.RealmPrimaryKeyConstraintException

class RealmDBManager {
    private var realm = Realm.getDefaultInstance()
    companion object{
        @Volatile private var INSTANCE: RealmDBManager ? = null
        fun  getInstance(): RealmDBManager {
            return INSTANCE?: synchronized(this){
                RealmDBManager().also {
                    INSTANCE = it
                }
            }
        }
    }
    constructor(){
        realm =Realm.getDefaultInstance()
    }

    fun <T : RealmModel>addData(value : T){
        realm.beginTransaction()
        realm.copyToRealmOrUpdate(value, ImportFlag.CHECK_SAME_VALUES_BEFORE_SET)
        realm.commitTransaction()
    }

    fun <T : RealmModel>getItems(clazz: Class<T>) : List<T>{
        return realm.where(clazz).findAll()
    }

    fun <T : RealmModel>getObject(clazz: Class<T>,key : String,value  : String) : T?{
        return realm.where(clazz).equalTo(key,value).findFirst()
    }

    fun <T : RealmModel>deleteAllData(clazz: Class<T>){
        return realm.delete(clazz)
    }
    fun <T : RealmObject> getData(realmObjCls: Class<T>): T? {
        return if (realm != null) {
            realm.where(realmObjCls).findFirst()
        } else null
    }
    fun deleteAllData() {
        if(realm!=null){
            realm.beginTransaction()
            realm.deleteAll()
            realm.commitTransaction()
        }

    }
    fun <T : RealmObject> deleteTable(realmObjCls: Class<T>) {
        if (realm != null) {
            realm.beginTransaction()
            try {
                realm.delete(realmObjCls)
                realm.commitTransaction()
            } catch (e: RealmPrimaryKeyConstraintException) {
                e.printStackTrace()
                realm.cancelTransaction()
            }

        }
    }
}