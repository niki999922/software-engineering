package com.kochetkov.actors

import com.kochetkov.core.SearchEngine.NetSource
import java.util.concurrent.CompletableFuture
import akka.actor.UntypedActor
import akka.actor.Props
import akka.actor.ReceiveTimeout
import scala.concurrent.duration.Duration
import java.util.HashMap
import java.util.function.Consumer

class MainActor(
    private val sources: List<NetSource>,
    duration: Duration?,
    private val futureResult: CompletableFuture<MainActorResult>
) : UntypedActor() {
    private val mainActorResult = MainActorResult()

    init {
        context.setReceiveTimeout(duration)
    }

    class MainActorResult {
        var result = HashMap<String, String>()
    }

    override fun onReceive(obj: Any) {
        when (obj) {
            is String -> {
                sendRequest(obj)
            }
            is ChildActor.ChildActorResultMessage -> {
                obj.run {
                    mainActorResult.result[source.name] = response
                    if (mainActorResult.result.size == sources.size) returnResult()
                }
            }
            is ReceiveTimeout -> {
                returnResult()
            }
        }
    }

    private fun sendRequest(request: String) {
        if (!context().children().isEmpty) {
            return
        }
        sources.forEach {
            context
                .actorOf(Props.create(ChildActor::class.java, it))
                .tell(request, self)
        }
    }

    private fun returnResult() {
        futureResult.complete(mainActorResult)
        context.system().stop(self)
    }
}