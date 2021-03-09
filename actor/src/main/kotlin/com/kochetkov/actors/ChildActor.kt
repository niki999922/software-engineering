package com.kochetkov.actors

import com.kochetkov.core.SearchEngine.NetSource
import akka.actor.UntypedActor
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class ChildActor(var source: NetSource) : UntypedActor() {
    override fun onReceive(obj: Any) {
        when (obj) {
            is String -> {
                val uri = URI.create("http://${source.host}:${source.port}/find?name=${obj}")

                val client = HttpClient.newBuilder().build()
                val request = HttpRequest.newBuilder().uri(uri).build()
                val response = client.send(request, HttpResponse.BodyHandlers.ofString()).body().intern()

                sender().tell(ChildActorResultMessage(response, source), self)
            }
        }
    }

    class ChildActorResultMessage(var response: String, var source: NetSource)
}