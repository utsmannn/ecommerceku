package com.utsman.apis.product.model.local

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class WishlistItemRealm : RealmObject {
    @PrimaryKey
    var id: Int = 0
    var name: String = ""
    var shortDescription: String = ""
    var category: CategoryItemRealm? = null
    var price: Double = 0.0
    var rating: Double = 0.0
    var discount: Int = 0
    var images: String = ""
}

class CategoryItemRealm : RealmObject {
    @PrimaryKey
    var id: Int = 0
    var name: String = ""
    var description: String = ""
}