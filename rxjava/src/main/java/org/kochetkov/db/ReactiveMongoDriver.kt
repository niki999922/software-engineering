package org.kochetkov.db

import com.mongodb.client.model.Filters
import com.mongodb.rx.client.MongoClient
import com.mongodb.rx.client.MongoClients
import com.mongodb.rx.client.Success
import org.kochetkov.model.Product
import org.kochetkov.model.User
import rx.Observable

class ReactiveMongoDriver(url: String) {
    init {
        client = MongoClients.create(url)
    }

    fun addUser(user: User): Observable<Success> {
        return client.getDatabase(DATABASE_NAME)
            .getCollection(USER_COLLECTION)
            .insertOne(user.asDocument())
    }

    fun addProduct(product: Product): Observable<Success> {
        return client.getDatabase(DATABASE_NAME)
            .getCollection(PRODUCT_COLLECTION)
            .insertOne(product.asDocument())
    }

    val allProducts: Observable<Product>
        get() = client.getDatabase(DATABASE_NAME)
            .getCollection(PRODUCT_COLLECTION)
            .find()
            .toObservable().map(::Product)

    fun getUser(id: Int): Observable<User> {
        return client.getDatabase(DATABASE_NAME)
            .getCollection(USER_COLLECTION)
            .find(Filters.eq("id", id))
            .toObservable().map(::User)
    }

    companion object {
        private lateinit var client: MongoClient
        const val MONGO_DB_URL = "mongodb://localhost:27017"
        private const val DATABASE_NAME = "rxjava_db_1"
        private const val USER_COLLECTION = "users"
        private const val PRODUCT_COLLECTION = "products"
    }
}