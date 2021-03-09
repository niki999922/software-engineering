package com.kochetkov.core

import java.io.Closeable
import org.mockserver.integration.ClientAndServer
import org.mockserver.model.HttpStatusCode
import org.mockserver.model.HttpRequest
import org.mockserver.model.HttpResponse
import scala.concurrent.duration.Duration

class StubServer(port: Int, timeout: Duration) : Closeable {
    private val stubServer: ClientAndServer = ClientAndServer.startClientAndServer(port)

    init {
        stubServer.`when`(
            HttpRequest.request()
                .withMethod("GET")
                .withPath("/find")
        ).respond { request: HttpRequest ->
            Thread.sleep(timeout.toMillis().coerceAtLeast(0))
            request.getFirstQueryStringParameter("name").let { name ->
                HttpResponse.response()
                    .withStatusCode(HttpStatusCode.OK_200.code())
                    .withBody(name)
            }
        }
    }

    override fun close() {
        stubServer.close()
    }
}