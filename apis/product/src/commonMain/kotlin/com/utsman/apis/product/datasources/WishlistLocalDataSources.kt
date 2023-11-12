package com.utsman.apis.product.datasources

import com.utsman.apis.product.model.local.CategoryItemRealm
import com.utsman.apis.product.model.local.WishlistItemRealm
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.query.find
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class WishlistLocalDataSources {

    private val config = RealmConfiguration.create(schema = setOf(WishlistItemRealm::class, CategoryItemRealm::class))
    private val realm = Realm.open(config)

    suspend fun getAllItem(): Flow<List<WishlistItemRealm>> {
         return withContext(Dispatchers.IO) {
            realm.query(WishlistItemRealm::class)
                .find()
                .asFlow()
                .map {
                    it.list.asReversed()
                }
        }
    }

    suspend fun insertItem(wishlistItemRealm: WishlistItemRealm) {
        withContext(Dispatchers.IO) {
            realm.write {
                copyToRealm(wishlistItemRealm, updatePolicy = UpdatePolicy.ALL)
            }
        }
    }

    suspend fun deleteItem(id: Int) {
        withContext(Dispatchers.IO) {
            realm.write {
                query(WishlistItemRealm::class, "id = $id")
                    .find {
                        delete(it)
                    }
            }
        }
    }

    suspend fun isItemExist(id: Int): Flow<Boolean> {
        return withContext(Dispatchers.IO) {
            realm.query(WishlistItemRealm::class, "id = $id")
                .find()
                .asFlow()
                .map {
                    it.list.isNotEmpty()
                }
        }
    }

    fun close() {
        realm.close()
    }

}