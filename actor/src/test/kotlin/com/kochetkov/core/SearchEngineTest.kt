package com.kochetkov.core;

import org.junit.Test;

import scala.concurrent.duration.Duration;
import com.kochetkov.core.SearchEngine.*;
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue

import java.util.concurrent.TimeUnit;

class SearchEngineTest {
    companion object {
        const val LOCALHOST = "localhost"
        const val PORT1 = 8130
        const val PORT2 = 8131

        val ZERO_SECOND: Duration = Duration.create(0, TimeUnit.MILLISECONDS);
        val ONE_SECOND: Duration = Duration.create(1000, TimeUnit.MILLISECONDS);
        val TWO_SECOND: Duration = Duration.create(2000, TimeUnit.MILLISECONDS);
    }

    @Test
    fun `result data have to be wow`() {
        StubServer(PORT1, ZERO_SECOND).use {
            val phrase = "wow"
            val source = NetSource(LOCALHOST, PORT1, "my name is cake")
            val searchEngine = SearchEngine()
            searchEngine.addSource(source)

            val result = searchEngine.search(phrase, ONE_SECOND)

            assertEquals(1, result.size)
            assertTrue(result.containsKey(source.name))
            assertEquals(phrase, result[source.name])
        }
    }

    @Test
    fun `allow run search on two sources`() {
        StubServer(PORT1, ZERO_SECOND).use {
            StubServer(PORT2, ZERO_SECOND).use {
                val searchEngine = SearchEngine()
                searchEngine
                    .addSource(NetSource(LOCALHOST, PORT1, "1"))
                    .addSource(NetSource(LOCALHOST, PORT2, "2"))

                val result = searchEngine.search("wow", ONE_SECOND)

                assertEquals(2, result.size)
                assertTrue(result.containsKey("1"))
                assertTrue(result.containsKey("2"))
            }
        }
    }

    @Test
    fun `one source was lazy so returned one of two sources`() {
        StubServer(PORT1, TWO_SECOND).use {
            StubServer(PORT2, ZERO_SECOND).use {
                val searchEngine = SearchEngine()
                searchEngine
                    .addSource(NetSource(LOCALHOST, PORT1, "1"))
                    .addSource(NetSource(LOCALHOST, PORT2, "2"))

                val result = searchEngine.search("wow", ONE_SECOND)

                assertEquals(1, result.size)
                assertTrue(result.containsKey("2"))
            }
        }
    }
}
