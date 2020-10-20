class VKClientImpl(private val vkService: VKService) : VKClient {
    override fun getStatistic(tag: String, hours: Int, currentTime: Long): List<Int> {
        val result = mutableListOf<Int>()

        val dates = vkService.getDates(tag, hours, currentTime)
        for (hour in 1..hours) {
            val right = currentTime - (hour - 1) * HOUR
            val left = currentTime - hour * HOUR
            result.add(dates.filter { it * 1000 in left until right }.size)
        }

        return result
    }

    companion object {
        const val HOUR = 60L * 60 * 1000
    }
}