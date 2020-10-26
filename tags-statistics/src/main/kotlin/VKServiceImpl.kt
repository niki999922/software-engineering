import ResponseHandler.Companion.datesList
import java.net.URL
import java.util.*

open class VKServiceImpl(private val api: String = API_TAG_STATISTIC_URL) : VKService {
    companion object {
        const val SERVICE_ACCESS_TOKEN = "ca66d33fca66d33fca66d33f7bca0dca44cca66ca66d33f9767fb518f99a98dc3728f72"
        const val API_TAG_STATISTIC_URL = "https://api.vk.com/method/newsfeed.search"
        const val API_VERSION = "5.124"

        fun List<Long>.log() = this.map { Date(it * 1000) }.forEach { println(it) }
    }


    override fun getDates(tag: String, hours: Int, currentTime: Long): List<Long> {
        val response = sendRequest("$api?${collectParameters(tag, hours, currentTime)}")
        return response.datesList().sorted()
    }

    private fun sendRequest(url: String) = URL(url).readText()

    private fun collectParameters(tag: String, hours: Int, currentTime: Long) =
        RequestParametersBuilder()
            .add("access_token", SERVICE_ACCESS_TOKEN)
            .add("start_time", (currentTime - hours.fromHours()).toString())
            .add("v", API_VERSION)
            .add("q", tag)
            .toString()

    /**
     * return millis
     */
    private fun Int.fromHours() = this * 60 * 60 * 1000
}