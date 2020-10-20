fun main() {
    val client = VKClientImpl(VKServiceImpl())

    val response = client.getStatistic("spb", 3, System.currentTimeMillis())
    println(response)
}