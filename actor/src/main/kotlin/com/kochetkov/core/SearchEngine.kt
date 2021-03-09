package com.kochetkov.core

import akka.actor.ActorSystem
import java.util.concurrent.CompletableFuture
import com.kochetkov.actors.MainActor.MainActorResult
import akka.actor.ActorRef
import akka.actor.Props
import com.kochetkov.actors.MainActor
import scala.concurrent.duration.Duration

class SearchEngine {
    private val sources = mutableListOf<NetSource>()

    data class NetSource(val host: String, val port: Int, val name: String)

    fun addSource(source: NetSource): SearchEngine = this.also { sources.add(source) }

    fun search(message: String, duration: Duration): Map<String, String> {
        val system: ActorSystem = ActorSystem.create("SearchEngine")
        val futureResult = CompletableFuture<MainActorResult>()

        return try {
            val main = system.actorOf(Props.create(MainActor::class.java, sources, duration, futureResult))
            main.tell(message, ActorRef.noSender())
            futureResult.get().result
        } finally {
            system.terminate()
        }
    }
}