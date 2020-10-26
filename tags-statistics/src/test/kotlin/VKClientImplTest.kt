import com.xebialabs.restito.builder.stub.StubHttp.whenHttp
import com.xebialabs.restito.semantics.Action.stringContent
import com.xebialabs.restito.semantics.Condition.method
import com.xebialabs.restito.semantics.Condition.startsWithUri
import com.xebialabs.restito.server.StubServer
import org.glassfish.grizzly.http.Method
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations


internal class VKClientImplTest {
    private lateinit var vkClientImpl: VKClientImpl
    private lateinit var vkClientImplForStub: VKClientImpl

    companion object {
        private const val PORT = 10001
    }

    @Mock
    var vkService: VKServiceImpl = VKServiceImpl()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        vkClientImpl = VKClientImpl(vkService)

    }

    @Test
    fun `have to return array 2, 0, 0`() {
        val currentTime = System.currentTimeMillis()
        `when`(vkService.getDates("tag", 3, currentTime)).thenReturn(listOf(currentTime / 1000 - 2, currentTime / 1000))
        val result = vkClientImpl.getStatistic("tag", 3, currentTime)
        Assert.assertEquals(2, result.first())
    }

    @Test
    fun `have to return array 1 0 0`() {
        val currentTime = System.currentTimeMillis()
        withStubServer(PORT) { s ->
            whenHttp(s)
                .match(method(Method.GET), startsWithUri("/custom-api"))
                .then(stringContent("""
                            {
          "response": {
            "items": [
              {
                "id": 110270,
                "date": ${currentTime / 1000},
                "owner_id": 107933267,
                "from_id": 107933267,
                "post_type": "post",
                "text": "asdasd",
                "is_favorite": false
              }
            ],
            "total_count": 4955
          }
        }
                """.trimIndent()))
            val path = "http://localhost:$PORT/custom-api"

            vkClientImplForStub = VKClientImpl(VKServiceImpl(path))

            val res = vkClientImplForStub.getStatistic("tag", 3, currentTime)

            Assert.assertEquals(1, res.first())
        }
    }


    private fun withStubServer(port: Int, callback: (StubServer?) -> Unit) {
        var stubServer: StubServer? = null
        try {
            stubServer = StubServer(port).run()
            callback(stubServer)
        } finally {
            stubServer?.stop()
        }
    }
}