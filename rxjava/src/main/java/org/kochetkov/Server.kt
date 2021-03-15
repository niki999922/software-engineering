package org.kochetkov

import io.netty.handler.codec.http.HttpResponseStatus
import io.reactivex.netty.protocol.http.server.HttpServer
import org.kochetkov.db.ReactiveMongoDriver
import org.kochetkov.db.ReactiveMongoDriver.Companion.MONGO_DB_URL
import org.kochetkov.model.Currency
import org.kochetkov.model.Product
import org.kochetkov.model.User
import rx.Observable

typealias Action = String

object Server {
    private var driver = ReactiveMongoDriver(MONGO_DB_URL)

    @JvmStatic
    fun main(args: Array<String>) {
        HttpServer
            .newServer(8080)
            .start { request, response ->
                request.decodedPath.substring(request.decodedPath.lastIndexOf("/") + 1).let { action ->
                    response.writeString(run {
                        var responseMessage = Observable.just("")

                        runCatching {
                            responseMessage = action.handleMapping(request.queryParameters)
                        }.onFailure {
                            responseMessage = Observable.just(it.message)
                            response.status = HttpResponseStatus.BAD_REQUEST
                        }
                        responseMessage
                    })
                }
            }
            .awaitShutdown()
    }

    private fun Action.handleMapping(queryParameters: Map<String, List<String>>): Observable<String> {
        return when (this) {
            "register" -> ::handleRegistration
            "add-product" -> ::handleAddProduct
            "product" -> ::handleGetProducts
            else -> throw RuntimeException("Incorrect command, try another action")
        }(queryParameters)
    }

    private fun handleGetProducts(queryParameters: Map<String, List<String>>) =
        queryParameters["id"]!!.first().toInt().let { id ->
            driver.getUser(id).map(User::currency)
                .flatMap { currency: Currency ->
                    driver.allProducts
                        .map { "${it.changeCurrency(currency)}\n" }
                }
        }

    private fun handleAddProduct(queryParameters: Map<String, List<String>>) =
        (queryParameters["name"]!!.first()
                to queryParameters["value"]!!.first().toDouble()).let { (name, value) ->
            Currency.valueOf(queryParameters["currency"]!!.first()).let { currency ->
                driver.addProduct(Product(name, value, currency)).map { "Product $name inserted: code $it" }
            }
        }

    private fun handleRegistration(queryParameters: Map<String, List<String>>) =
        (queryParameters["id"]!!.first().toInt()
                to Currency.valueOf(queryParameters["currency"]!!.first())).let { (id, currency) ->
            driver.addUser(User(id, currency)).map { "User $id inserted: code $it" }
        }
}
