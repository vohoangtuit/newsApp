package vht.com.news.models

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.io.Serializable

open class UserModel : RealmObject, Serializable {
    @PrimaryKey
    @SerializedName("username")
    var username: String? = null


    @SerializedName("email")
    var email: String? = null

    @SerializedName("password")
    var password: String? = null

    constructor(username: String,email: String, password: String){
        this.username =username
        this.email =email
        this.password =password
    }
    constructor()
}