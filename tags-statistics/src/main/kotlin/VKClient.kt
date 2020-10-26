interface VKClient {
    fun getStatistic(tag: String, hours: Int, currentTime: Long): List<Int>
}