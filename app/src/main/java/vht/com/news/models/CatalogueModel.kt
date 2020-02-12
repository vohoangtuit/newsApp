package vht.com.news.models

class CatalogueModel {
    var id: String?=null
    var name: String? =null
    var selected: Boolean=false
    constructor(id:String,name: String,selected: Boolean ){
        this.id =id
        this.name =name
        this.selected =selected
    }
}